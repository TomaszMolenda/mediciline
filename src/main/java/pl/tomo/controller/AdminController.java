package pl.tomo.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.User;
import pl.tomo.service.BackupService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BackupService backupService;
	
	
	@RequestMapping(value = "/info")
	public ModelAndView admin(Principal principal) throws Exception {
		ModelAndView modelAndView = new ModelAndView("admin");
		modelAndView.addObject("backups", backupService.findAll());
		modelAndView.addObject("users", userService.findAll());
		return modelAndView;
	}
	@RequestMapping(value = "/postgresql/backup")
	public ModelAndView postgresqlBackup(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findByRequest(request);
		boolean isAdmin = userService.isAdmin(user);
		if(isAdmin) {
			try {
				backupService.backupPostgres();
			} catch (IOException e) {
				generateBackupError(modelAndView);
				return modelAndView;
			}
			modelAndView.setViewName("redirect:/admin/info.html?success=true#tab_databases");
		} else
			generateBackupError(modelAndView);
		return modelAndView;
	}
	private void generateBackupError(ModelAndView modelAndView) {
		modelAndView.setViewName("redirect:/admin/info.html?success=false#tab_databases");
	}
	
	@RequestMapping(value = "/postgresql/restore")
	@ResponseStatus(value = HttpStatus.OK)
	public void postgresqlRestore(@RequestHeader("id") int id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		User user = userService.findByRequest(request);
		boolean isAdmin = userService.isAdmin(user);
		if(isAdmin) {
			try {
				backupService.restorePostgres(id);
			} catch (IOException e) {
				response.setHeader("status", "unsuccess");
			}
			response.setHeader("status", "success");
		} else
			response.setHeader("status", "noaccess");
		
	}

	

}
