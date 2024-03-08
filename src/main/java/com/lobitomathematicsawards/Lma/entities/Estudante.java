package com.lobitomathematicsawards.Lma.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Data
@Entity
public class Estudante {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String name;
	private String nivel;
	private String escola;
	private String ano;
	private int whatsapp;
	private int tel;
	private String genero;
	private String morada;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	
}
