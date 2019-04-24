package com.intuit.cg.backendtechassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.dto.ProjectDTO;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.service.IProjectService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;

@RestController
@RequestMapping(RequestMappings.PROJECTS)
public class ProjectController {

	@Autowired
	IProjectService projectService;

	@Autowired
	private EntityToDTOConverterUtil dtoConverterUtil;

	@GetMapping
	public ResponseEntity<List<ProjectDTO>> getAllProjects() {
		final List<Project> projectList = projectService.findAllProjects();

		return new ResponseEntity<>(projectList.stream().map(project -> dtoConverterUtil.convertToProjectDTO(project))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
		
		final Project project = projectService.findProjectById(id);
		
		if(project!=null){			
			return new ResponseEntity<>(dtoConverterUtil.convertToProjectDTO(project),
				HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<ProjectDTO> addProject(@Valid @RequestBody Project project) throws Exception {

		return new ResponseEntity<>(dtoConverterUtil.convertToProjectDTO(projectService.createProject(project)),
				HttpStatus.CREATED);
	}

}
