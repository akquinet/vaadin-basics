package de.akquinet.engineering.vaadin.exercises.resources;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.akquinet.engineering.vaadin.ComponentView;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class ResourcesView implements View, ComponentView
{
    public static final String VIEW_NAME = "resources";

    private final VerticalLayout rootLayout = new VerticalLayout();

    private File file;

    public ResourcesView()
    {
        final Label title = new Label("Uploading a Resource");
        title.setStyleName(ValoTheme.LABEL_H2);

        final Image image = new Image("my favorite picture", new ThemeResource("img/placeholder.png"));
        image.setHeight("400px");

        final ProgressBar progressBar = new ProgressBar();
        progressBar.setWidth("300px");
        progressBar.setVisible(false);

        final Upload upload = new Upload();
        upload.setCaption("Upload image file");
        upload.setButtonCaption("upload");

        upload.setReceiver(getUploadReceiver(upload));

        upload.addSucceededListener(event -> image.setSource(new FileResource(file)));

        upload.addFailedListener(event -> Notification.show("Upload failed!", Notification.Type.ERROR_MESSAGE));

        upload.addStartedListener(event ->
        {
            progressBar.setValue(0.0f);
            progressBar.setVisible(true);
        });

        upload.addProgressListener((readBytes, contentLength) ->
        {
            if (contentLength < 0)
            {
                progressBar.setIndeterminate(true);
            }
            else
            {
                progressBar.setIndeterminate(false);
                progressBar.setValue((float) readBytes / contentLength);
            }
        });

        upload.addFinishedListener(event -> progressBar.setVisible(false));

        rootLayout.addComponents(title, upload, image, progressBar);

        rootLayout.addDetachListener(e -> UI.getCurrent().setPollInterval(-1));
    }

    private Upload.Receiver getUploadReceiver(final Upload upload)
    {
        return (filename, mimeType) ->
        {
            try
            {
                final MimeType mime = new MimeType(mimeType);
                if (!mime.getPrimaryType().equalsIgnoreCase("image"))
                {
                    upload.interruptUpload();
                }
            }
            catch (final MimeTypeParseException e)
            {
                e.printStackTrace();
                upload.interruptUpload();
            }

            final String tmpDir = System.getProperty("java.io.tmpdir");
            final File uploadsDir = Paths.get(tmpDir, "uploads").toFile();
            if (!uploadsDir.exists())
            {
                uploadsDir.mkdir();
            }

            file = new File(uploadsDir, filename);
            try
            {
                return new FileOutputStream(file);
            }
            catch (final FileNotFoundException e)
            {
                e.printStackTrace();
                upload.interruptUpload();
            }
            return null;
        };
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {
        UI.getCurrent().setPollInterval(500);
    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
