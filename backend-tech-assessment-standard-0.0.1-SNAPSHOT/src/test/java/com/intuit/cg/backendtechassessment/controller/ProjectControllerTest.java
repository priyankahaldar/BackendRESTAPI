package com.intuit.cg.backendtechassessment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intuit.cg.backendtechassessment.dto.ProjectDTO;
import com.intuit.cg.backendtechassessment.model.Project;
import com.intuit.cg.backendtechassessment.service.IProjectService;
import com.intuit.cg.backendtechassessment.util.EntityToDTOConverterUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean 
	private IProjectService projectService;
	
	@MockBean 
	private EntityToDTOConverterUtil dtoConverterUtil;
	
	@Test
    public void getMapping_SellerIsFound() throws Exception {
    	
    	final Long projectId = 100L;                
                
        final Project projectExpected = new Project();
        projectExpected.setProjectId(projectId);
        projectExpected.setProjectDescription("Test desc");
        projectExpected.setMaxBudget(1000.0);
        projectExpected.setDeadlineForBids(DateTime.now().plusDays(1).toDate());
        
        final ProjectDTO projectDTOExpected = createProjectDTO(projectExpected);
                
        Mockito.doReturn(projectExpected).when(projectService).findProjectById(projectId); 
        Mockito.doReturn(projectDTOExpected).when(dtoConverterUtil).convertToProjectDTO(Mockito.any(Project.class));         
                     
        mockMvc.perform(get("/projects/100")).andExpect(status().isOk())
        	.andExpect(jsonPath("$.projectId").value(projectDTOExpected.getProjectId()))
        	.andExpect(jsonPath("$.projectDescription").value(projectDTOExpected.getProjectDescription()))
        	.andExpect(jsonPath("$.maxBudget").value(projectDTOExpected.getMaxBudget()));
        	
        Mockito.verify( projectService, Mockito.times(1)).findProjectById(projectId);        
    } 
	
	@Test
    public void getMapping_SellerIsNotFound() throws Exception {
    	
    	final Long projectId = 100L;                
                
        final Project projectExpected = new Project();
        projectExpected.setProjectDescription("Test desc");
        projectExpected.setMaxBudget(1000.0);
        projectExpected.setDeadlineForBids(DateTime.now().toDate());
        
        final ProjectDTO projectDTOExpected = createProjectDTO(projectExpected);
                
        Mockito.doReturn(null).when(projectService).findProjectById(projectId); 
        Mockito.doReturn(projectDTOExpected).when(dtoConverterUtil).convertToProjectDTO(Mockito.any(Project.class));         
                     
        mockMvc.perform(get("/projects/100")).andExpect(status().isNotFound());
		               
        Mockito.verify( projectService, Mockito.times(1)).findProjectById(projectId);        
    } 

	ProjectDTO createProjectDTO(Project project ){		
		final ProjectDTO dto = new ProjectDTO();
		dto.setProjectId(project.getProjectId());
		dto.setProjectDescription(project.getProjectDescription());
		dto.setMaxBudget(project.getMaxBudget());
		dto.setDeadlineForBids(project.getDeadlineForBids());
		
		return dto;		
	}
}