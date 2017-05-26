package de.akquinet.engineering.vaadin.exercises.grid;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.akquinet.engineering.vaadin.ComponentView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class EditableGridView implements View, ComponentView
{
    public static final String VIEW_NAME = "editablegrid";

    private final VerticalLayout rootLayout = new VerticalLayout();

    public EditableGridView()
    {
        final List<Player> playerList = PlayerGenerator.createPlayers(10);

        final Grid<Player> grid = new Grid<>(Player.class);
        grid.setItems(playerList);

        grid.setColumns("name", "dateOfBirth");

        final Binder<Player> binder = grid.getEditor().getBinder();

        final TextField nameField = new TextField();
        grid.getColumn("name").setEditorBinding(binder.forField(nameField)
                .asRequired("name is mandatory")
                .bind("name"));

        final DateField dateField = new DateField();
        dateField.setDateFormat("yyyy-MM-dd");
        grid.getColumn("dateOfBirth").setEditorBinding(binder.bind(dateField, "dateOfBirth"));

        final Grid.Column<Player, String> genderColumn = grid
                .addColumn(player ->
                {
                    final GenderPresentation genderPresentation = GenderPresentation
                            .getPresentation(player.getGender());
                    return String.format("%s %s",
                                         genderPresentation
                                                 .getIcon().getHtml(),
                                         genderPresentation.getName());
                })
                .setRenderer(new HtmlRenderer())
                .setCaption("Sex");

        final ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.setItems(Gender.values());
        genderComboBox.setItemIconGenerator((IconGenerator<Gender>) gender -> GenderPresentation
                .getPresentation(gender).getIcon());
        genderComboBox
                .setItemCaptionGenerator((ItemCaptionGenerator<Gender>) gender -> GenderPresentation
                        .getPresentation(gender).getName());
        genderComboBox.setEmptySelectionAllowed(false);
        final Binder.Binding<Player, Gender> genderBinding = binder
                .bind(genderComboBox, "gender");
        genderColumn.setEditorBinding(genderBinding);

        grid.getEditor().setEnabled(true);

        grid.setHeightByRows(10d);
        rootLayout.setSizeFull();
        rootLayout.addComponent(grid);

        grid.getEditor().addSaveListener(event -> Notification.show(event.getBean().toString()));

        final Label showValuesLabel = new Label();
        showValuesLabel.setContentMode(ContentMode.HTML);
        final Button showPlayersButton = new Button("show players",
                e -> showValuesLabel.setValue(playerList
                        .stream()
                        .map(p -> p.toString())
                        .collect(Collectors.joining("<br/>"))));
        rootLayout.addComponents(showPlayersButton, showValuesLabel);
        rootLayout.setExpandRatio(showValuesLabel, 1f);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event)
    {

    }

    @Override
    public Component getComponent()
    {
        return rootLayout;
    }
}
