/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.scenariosimulation.client.editor;

import java.util.Optional;

import com.google.gwt.core.client.GWT;
import org.drools.workbench.screens.scenariosimulation.client.type.ScenarioSimulationResourceType;
import org.drools.workbench.screens.scenariosimulation.client.widgets.ScenarioGrid;
import org.drools.workbench.screens.scenariosimulation.client.widgets.ScenarioGridPanel;
import org.drools.workbench.screens.scenariosimulation.model.ScenarioSimulationModel;
import org.drools.workbench.screens.scenariosimulation.model.ScenarioSimulationModelContent;
import org.drools.workbench.screens.scenariosimulation.service.ScenarioSimulationService;
import org.guvnor.common.services.project.client.context.WorkspaceProjectContext;
import org.guvnor.common.services.shared.metadata.model.Overview;
import org.guvnor.messageconsole.client.console.widget.button.AlertsButtonMenuItemBuilder;
import org.kie.workbench.common.services.datamodel.model.PackageDataModelOracleBaselinePayload;
import org.kie.workbench.common.widgets.client.datamodel.AsyncPackageDataModelOracleFactory;
import org.kie.workbench.common.widgets.client.menu.FileMenuBuilder;
import org.kie.workbench.common.widgets.configresource.client.widget.bound.ImportsWidgetPresenter;
import org.kie.workbench.common.widgets.metadata.client.KieEditorWrapperView;
import org.kie.workbench.common.widgets.metadata.client.widget.OverviewWidgetPresenter;
import org.mockito.Mock;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.ext.editor.commons.client.history.VersionRecordManager;
import org.uberfire.ext.editor.commons.client.validation.DefaultFileNameValidator;
import org.uberfire.mocks.EventSourceMock;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.events.NotificationEvent;
import org.uberfire.workbench.model.menu.MenuItem;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractScenarioSimulationEditorTest {

    @Mock
    protected KieEditorWrapperView mockKieView;

    @Mock
    protected OverviewWidgetPresenter mockOverviewWidget;

    @Mock
    protected VersionRecordManager mockVersionRecordManager;

    @Mock
    protected FileMenuBuilder mockFileMenuBuilder;

    @Mock
    protected DefaultFileNameValidator mockFileNameValidator;

    @Mock
    protected ScenarioSimulationService scenarioSimulationService;

    @Mock
    protected ObservablePath path;

    @Mock
    protected Overview overview;

    @Mock
    protected WorkspaceProjectContext mockWorkbenchContext;

    @Mock
    protected AlertsButtonMenuItemBuilder mockAlertsButtonMenuItemBuilder;

    @Mock
    protected EventSourceMock<NotificationEvent> mockNotification;

    @Mock
    protected ImportsWidgetPresenter importsWidget;

    @Mock
    protected AsyncPackageDataModelOracleFactory oracleFactory;

    @Mock
    protected ScenarioGridPanel scenarioGridPanel;

    @Mock
    protected ScenarioGrid scenarioGrid;

    protected ScenarioSimulationResourceType type;

    protected ScenarioSimulationModelContent content;
    protected ScenarioSimulationModel model;

    public void setup() {
        this.type = GWT.create(ScenarioSimulationResourceType.class);

        //Mock FileMenuBuilder usage since we cannot use FileMenuBuilderImpl either
        when(mockFileMenuBuilder.addSave(any(MenuItem.class))).thenReturn(mockFileMenuBuilder);
        when(mockFileMenuBuilder.addCopy(any(ObservablePath.class), any(DefaultFileNameValidator.class))).thenReturn(mockFileMenuBuilder);
        when(mockFileMenuBuilder.addRename(any(Command.class))).thenReturn(mockFileMenuBuilder);
        when(mockFileMenuBuilder.addDelete(any(ObservablePath.class))).thenReturn(mockFileMenuBuilder);
        when(mockFileMenuBuilder.addValidate(any(Command.class))).thenReturn(mockFileMenuBuilder);
        when(mockFileMenuBuilder.addNewTopLevelMenu(any(MenuItem.class))).thenReturn(mockFileMenuBuilder);

        when(mockVersionRecordManager.getCurrentPath()).thenReturn(path);
        when(mockVersionRecordManager.getPathToLatest()).thenReturn(path);

        when(mockWorkbenchContext.getActiveWorkspaceProject()).thenReturn(Optional.empty());

        this.model = new ScenarioSimulationModel();
        this.content = new ScenarioSimulationModelContent(model,
                                                          overview,
                                                          mock(PackageDataModelOracleBaselinePayload.class));

        when(scenarioSimulationService.loadContent(path)).thenReturn(content);

        when(scenarioGridPanel.getScenarioGrid()).thenReturn(scenarioGrid);
    }
}
