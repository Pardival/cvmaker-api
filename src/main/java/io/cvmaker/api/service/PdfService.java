package io.cvmaker.api.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
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
        // 1. Préparer le contexte Thymeleaf
        Context context = new Context();
        context.setVariable("cv", cv);

        // 2. Générer le HTML
        String htmlContent = templateEngine.process("french-resume-ats", context);

        // 3. Convertir en PDF avec Playwright
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.setContent(htmlContent);

            // Paramètres pour un rendu A4
            byte[] pdfBytes = page.pdf(new Page.PdfOptions()
                    .setFormat("A4")
                    .setPrintBackground(true));

            browser.close();
            return pdfBytes;
        }
    }
}