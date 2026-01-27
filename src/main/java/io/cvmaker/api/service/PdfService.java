package io.cvmaker.api.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.Margin;
import io.cvmaker.api.model.CV;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(CV cv) {
        // Génération du corps du cv (contenu html)
        Context context = new Context();
        context.setVariable("cv", cv);
        String htmlContent = templateEngine.process("french-resume-ats", context);

        /* Lancement du navigateur avec page PDF */
        try (Playwright playwright = Playwright.create()) {
            // Options de lancement pour stabilité
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setChannel("chrome")
                    .setHeadless(true));

            // Ouverture d'une nouvelle page dans le navigateur avec son contenu
            BrowserContext browserContext = browser.newContext();
            Page page = browserContext.newPage();
            page.setContent(htmlContent);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Afifchage en vision PDF
            byte[] pdfBytes = page.pdf(new Page.PdfOptions()
                    .setFormat("A4")
                    .setPrintBackground(true)
                    .setMargin(new Margin().setTop("0").setRight("0").setBottom("0").setLeft("0")));

            // On ferme proprement dans l'ordre
            browser.close();
            return pdfBytes;
        } catch (Exception e) {
            // Log l'erreur pour voir si c'est un problème d'installation de Chromium
            System.err.println("Erreur Playwright : " + e.getMessage());
            throw e;
        }
    }
}