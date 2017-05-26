package de.akquinet.engineering.vaadin.exercises.resources;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
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

        // Bonus: create a progress bar to show the progress of the upload

        // TODO: implement an upload button to upload images and show them on the page
        // Tips:
        // 1) create an instance of the Upload component
        // 2) set the upload receiver, use the getUploadReceiver method for that
        // 3) add a succeed listener to set the image's source with the uploaded file,
        //      use new FileResource(file) for the image source
        // 4) add a failed listener to show an error notification in case something went wrong

        // Bonus: add a started, a progress and a finished listener to show and hide the progress bar,
        //          also update its value with the current progress
        // Tip: You need polling (or the push mode) to transfer the current value of the progress bar to the client.
        //      Enable polling in enter() and disable it in the detach listener of the rootLayout.
        //      The method you use for enabling and disabling polling is UI.getCurrent().setPollInterval(..).

        rootLayout.addComponents(title, image);

        // TODO: add upload component and in case of the bonus task the progress bar
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
        // Bonus: start polling by setting the polling interval on the UI
    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
