package com.lobitomathematicsawards.Lma.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.lobitomathematicsawards.Lma.entities.Estudante;
import com.lobitomathematicsawards.Lma.repository.EstudanteRepository;

@Controller
@RequestMapping("/lma")
public class EstudanteController {

	@Autowired
	EstudanteRepository EstRepo;
	
	private static final String chaveS = "akm2015lma";
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/sobre")
	public String about() {
		return "about";
	}
	
	@GetMapping("/contestinfo")
	public String info() {
		return "constestinfo";
	}
	
	@GetMapping("/formulario")
	public ModelAndView formGet() {
		
		
		ModelAndView mv = new ModelAndView("form");
		
		List<Estudante> est = EstRepo.findAll();
		int total = est.size();
		
		mv.addObject("estudante", new Estudante());
		mv.addObject("total", total);
		return mv; 
		     
		   
	}       
	@PostMapping("/formulario")
	public String formPost(@ModelAttribute Estudante estudante, RedirectAttributes attributes) {
		Estudante existent = EstRepo.findByName(estudante.getName());
		if(existent != null) {
			attributes.addFlashAttribute("mensagemError", "Já existe um inscrito com este nome.");
			return "redirect:/lma/formulario";
		}
		  
		if(estudante.getName().isEmpty()|| estudante.getNivel().isEmpty() || estudante.getAno().isEmpty() || estudante.getEscola().isEmpty()
		|| estudante.getWhatsapp()== 0 || estudante.getTel() == 0 || estudante.getGenero().isEmpty() || estudante.getMorada().isEmpty()) {
			attributes.addFlashAttribute("mensagemError", "Verifique os campos do formulário");
			return "redirect:/lma/formulario"; 
		}       
		     
		String numeroComoString1 = String.valueOf(estudante.getTel());
		String numeroComoString2 = String.valueOf(estudante.getWhatsapp());
		// Contando os caracteres
        int lengthTel = numeroComoString1.length();
        int lengthWhat = numeroComoString2.length();
		
        if(lengthTel > 9 || lengthTel < 9 || lengthWhat > 9 || lengthWhat < 9 ) {
        	attributes.addFlashAttribute("mensagemError", "O numero de telefone ou o WhatsApp devem ter apenas 9 digitos");
			return "redirect:/lma/formulario";
        }
        var currentDate = LocalDateTime.now();
        estudante.setCreatedAt(currentDate);  
			EstRepo.save(estudante);
		//attributes.addFlashAttribute("mensagemError", "Inscrição feita com sucesso");
		
		return "redirect:/lma/fimdeinscricao";
	}      
	      
	@GetMapping("/fimdeinscricao")    
	public String link() {
		
		return "linkwhatsapp";
	}  
	 
	  
	@GetMapping("/inscritos/listagem")
	public ModelAndView list(@RequestParam("chave") String chave) {
		
		if (chaveS.equals(chave)) {
       
			ModelAndView mv = new ModelAndView("list");
			List<Estudante> estudantes = EstRepo.findAll();
			mv.addObject("estudante", estudantes);
			
			return mv;     
        } else { 
        	ModelAndView mv = new ModelAndView("non");
			List<Estudante> estudantes = EstRepo.findAll();
			mv.addObject("estudante", estudantes);
			
			return mv;
        }
	
	}
	
	@GetMapping("/inscritos/delete/{id}")
	public String delete(@PathVariable("id") UUID id) {
		Optional<Estudante> estudante =  EstRepo.findById(id);
		
		if(estudante != null) {
			EstRepo.delete(estudante.get());
		}
		
		return "redirect:/lma/formulario";
	}
	 
	
	

	
	
	
}
