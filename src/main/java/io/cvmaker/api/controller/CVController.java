package io.cvmaker.api.controller;

import io.cvmaker.api.dto.request.CVRequest;
import io.cvmaker.api.dto.response.*;
import io.cvmaker.api.mapper.CVMapper;
import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.PersonalDetails;
import io.cvmaker.api.model.User;
import io.cvmaker.api.service.CVService;
import io.cvmaker.api.service.UserService;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class CVController {
    private final CVService cvService;
    private final UserService userService;
    private final CVMapper cvMapper;
    private final TemplateEngine templateEngine;



    public CVController(CVService cvService, UserService userService, CVMapper cvMapper, TemplateEngine templateEngine) {
        this.cvService = cvService;
        this.userService = userService;
        this.cvMapper = cvMapper;
        this.templateEngine = templateEngine;
    }

    @GetMapping()
    public List<CVResponse> findCVByUserId(@AuthenticationPrincipal Jwt jwt) {
        return cvMapper.toResponse(cvService.findByUserId(jwt.getSubject()));
    }

    @PostMapping()
    public CVResponse saveCV(@AuthenticationPrincipal Jwt jwt, @RequestBody CVRequest request) {
        User user = userService.syncUser(jwt);
        return cvMapper.toResponse(cvService.saveCV(cvMapper.toEntity(request), user.getId()));
    }

    @PutMapping("/{id}")
    public CVResponse updateCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id, @RequestBody CVRequest request) {
        return cvMapper.toResponse(cvService.updateCV(jwt.getSubject(), id, cvMapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        cvService.deleteCV(jwt.getSubject(), id);
    }


    @GetMapping("/test-html")
    public String testHtml() {
        Context context = new Context();

        // Création d'un "Faux CV" complet pour tester le design
        CVResponse mockCv = new CVResponse();
        mockCv.setCvName("Mon Super CV");
        mockCv.setSummary("Développeur passionné avec 5 ans d'expérience dans la création d'applications web robustes.");
        mockCv.setLanguages(List.of(
                new LanguageResponse("Anglais", "Avancé"),
                new LanguageResponse("Français", "Native")));

        // Détails Personnels
        PersonalDetailsResponse details = new PersonalDetailsResponse();
        details.setFirstName("Thomas");
        details.setLastName("Neo");
        details.setGithubUrl("https://github.com/neo-matrix");
        details.setLinkedInUrl("https://www.linkedin.com/in/thomas-neo-matrix/");
        details.setCurrentJobTitle("Ingénieur Logiciel Senior");
        details.setEmail("neo@matrix.com");
        details.setPhone("06 99 99 99 99");
        details.setCity("Nantes");
        details.setCountry("France");
        mockCv.setPersonalDetails(details);

        // Compétences
        List<SkillResponse> skills = new ArrayList<>();
        skills.add(new SkillResponse("Java", "Expert"));
        skills.add(new SkillResponse("Spring Boot", "Avancé"));
        skills.add(new SkillResponse("Docker", "Intermédiaire"));
        mockCv.setSkills(skills);

        // Expériences
        List<ExperienceResponse> exps = new ArrayList<>();
        ExperienceResponse exp1 = new ExperienceResponse();
        exp1.setPosition("Lead Developer");
        exp1.setCompany("Tech Corp");
        exp1.setLocation("Nantes, France");
        exp1.setStartYear(2001);
        exp1.setEndYear(2024);
        exp1.setIsCurrent(true);
        exp1.setAchievements(List.of(
                "Gestion d'une équipe de 5 personnes. Migration vers le Cloud.",
                "Gestion d'applications scalable et performantes.",
                "Optimisation des performances des applications web."
                ));
        exps.add(exp1);
        mockCv.setExperiences(exps);

        ExperienceResponse exp2 = new ExperienceResponse();
        exp2.setPosition("Developpeur Java");
        exp2.setCompany("Tech Corp");
        exp2.setLocation("Nantes, France");
        exp2.setStartYear(2019);
        exp2.setEndYear(2021);
        exp2.setIsCurrent(false);
        exp2.setAchievements(List.of(
                "Développement d'applications web performantes",
                "Intégration de fonctionnalités avancées",
                "Optimisation des performances des applications web"
        ));
        exps.add(exp2);

        ExperienceResponse exp3 = new ExperienceResponse();
        exp3.setPosition("Developpeur Java");
        exp3.setCompany("Tech Corp");
        exp3.setLocation("Nantes, France");
        exp3.setStartYear(2019);
        exp3.setEndYear(2021);
        exp3.setIsCurrent(false);
        exp3.setAchievements(List.of(
                "Développement d'applications web performantes",
                "Intégration de fonctionnalités avancées",
                "Optimisation des performances des applications web"
        ));
        exps.add(exp3);


        List<EducationResponse> edus = new ArrayList<>();
        EducationResponse edu1 = new EducationResponse();
        edu1.setDegree("Master MIAGE");
        edu1.setInstitution("Université de Nantes");
        edu1.setStartYear(2019);
        edu1.setEndYear(2021);
        edu1.setLocation("Nantes, France");
        edu1.setAchievements(List.of(
                "Obtention d'un Master en Informatique Appliquée et Génie Logiciel",
                "Formation en développement web et technologies Cloud"
        ));
        edus.add(edu1);

        EducationResponse edu2 = new EducationResponse();
        edu2.setDegree("Licence Informatique");
        edu2.setInstitution("Université de Nantes");
        edu2.setStartYear(2016);
        edu2.setEndYear(2019);
        edu2.setLocation("Nantes, France");
        edu2.setAchievements(List.of(
                "Obtention d'une Licence en Informatique",
                "Formation en développement web et technologies Cloud"
        ));
        edus.add(edu2);

        EducationResponse edu3 = new EducationResponse();
        edu3.setDegree("Licence Informatique");
        edu3.setInstitution("Université de Nantes");
        edu3.setStartYear(2016);
        edu3.setEndYear(2019);
        edu3.setLocation("Nantes, France");
        edu3.setAchievements(List.of(
                "Obtention d'une Licence en Informatique",
                "Formation en développement web et technologies Cloud"
        ));
        edus.add(edu3);
        mockCv.setEducations(edus);

        // On injecte le tout dans la variable "cv" que le template attend
        context.setVariable("cv", mockCv);

        return templateEngine.process("french-resume-ats", context);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCv(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        byte[] pdfContents = cvService.generateCvPdf(jwt.getSubject(), id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Nom du fichier qui apparaîtra lors du téléchargement
        String filename = "CV_Export.pdf";
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(filename)
                .build());

        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
    }
}
