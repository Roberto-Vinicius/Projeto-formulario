package com.roberto.form.controller;

import com.roberto.form.dto.FormDTO;
import com.roberto.form.entity.Candidato;
import com.roberto.form.entity.enums.Escolariedade;
import com.roberto.form.repository.CandidateRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/form")
public class FormController {

    private final CandidateRepository candidateRepository;
    private final String UPLOAD_DIR = "./uploads/";
    
    @Autowired
    public FormController( CandidateRepository candidateRepository) {
        this.CandidateRepository = candidateRepository;
        
        // Criar diretório de uploads se não existir
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de uploads", e);
        }
        
        // Criar diretório de uploads se não existir
        this.candidateRepository = candidateRepository;
    }

    @GetMapping
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("formDTO", new FormDTO());
        mv.addObject("escolaridades", Escolariedade.values()); // Adiciona enum ao modelo
        return mv;
    }

    @PostMapping
    public ModelAndView submitForm(
            @RequestParam("arquivo") MultipartFile arquivo,
            @ModelAttribute("formDTO") @Valid FormDTO formDTO,
            BindingResult bindingResult,
            HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("escolaridades", Escolariedade.values()); // Mantém enum no modelo
        
        // Validação básica
        if (bindingResult.hasErrors()) {
            mv.setViewName("index");
            return mv;
        }

        // Validação do arquivo
        if (arquivo.isEmpty()) {
            bindingResult.rejectValue("arquivo", "arquivo.empty", "Por favor, envie seu currículo.");
            mv.setViewName("index");
            return mv;
        }

        if (!isValidFileType(arquivo)) {
            bindingResult.rejectValue("arquivo", "arquivo.type", "Apenas arquivos .doc, .docx ou .pdf são permitidos.");
            mv.setViewName("index");
            return mv;
        }

        if (arquivo.getSize() > 1048576) { // 1MB
            bindingResult.rejectValue("arquivo", "arquivo.size", "O tamanho máximo do arquivo é 1MB.");
            mv.setViewName("index");
            return mv;
        }

        // Salvar arquivo no sistema de arquivos
        String arquivoPath;
        try {
            arquivoPath = saveFile(arquivo);
        } catch (IOException e) {
            bindingResult.rejectValue("arquivo", "arquivo.error", "Erro ao salvar o arquivo.");
            mv.setViewName("index");
            return mv;
        }

        // Conversão DTO para Entity
        Candidato candidato = new Candidato();
        BeanUtils.copyProperties(formDTO, candidato);
        candidato.setArquivoPath(arquivoPath);
        candidato.setIp(request.getRemoteAddr());
        candidato.setDataEnvio(LocalDateTime.now());

        // Salvar no banco de dados
        candidateRepository.save(candidato);

        // Redirecionar para página de sucesso
        mv.setViewName("success");
        return mv;
    }

    private boolean isValidFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) return false;
        
        return fileName.toLowerCase().endsWith(".pdf") || 
               fileName.toLowerCase().endsWith(".doc") || 
               fileName.toLowerCase().endsWith(".docx");
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Gera um nome único para o arquivo
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, file.getBytes());
        return path.toString();
    }
}