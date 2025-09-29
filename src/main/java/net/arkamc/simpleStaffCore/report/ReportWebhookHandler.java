package net.arkamc.simpleStaffCore.report;

import java.io.File;
import java.util.HashMap;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.util.WebhookUtil;
import org.bukkit.entity.Player;

public class ReportWebhookHandler {

    private static final String WEBHOOK_URL = SimpleStaffCore.getInstance()
            .getConfigFile()
            .getString("report.webhook.webhook_url");

    public static void sendWebhook(Player reported, Player reporter, String reason) {
        File jsonFile = WebhookUtil.loadFile("report_webhook.json");

        String reportedName = reported.getName();
        String reporterName = reporter.getName();
        String reportedUUID = reported.getUniqueId().toString();
        String reporterUUID = reporter.getUniqueId().toString();

        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("{reported}", reportedName);
        placeholders.put("{reporter}", reporterName);
        placeholders.put("{reported_uuid}", reportedUUID);
        placeholders.put("{reporter_uuid}", reporterUUID);
        placeholders.put("{reason}", reason);

        try {
            WebhookUtil.sendWebhook(jsonFile, "report", WEBHOOK_URL, placeholders);
        } catch (Exception e) {
            SimpleStaffCore.getInstance().getLogger().warning("Failed to send report webhook: " + e.getMessage());
        }
    }

}
