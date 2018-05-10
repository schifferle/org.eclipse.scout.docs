/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.reflect.ConfigurationUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxForm.MainBox.ExampleBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxForm.MainBox.GroupBoxPropertiesBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox;

public class GroupBoxForm extends AbstractForm implements IPageForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBox");
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ExampleBox getExampleBox() {
    return getFieldByClass(ExampleBox.class);
  }

  public GroupBoxPropertiesBox getSettingsBox() {
    return getFieldByClass(GroupBoxPropertiesBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class ExampleBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Example GroupBox";
      }

      @Override
      protected void injectFieldsInternal(OrderedCollection<IFormField> fields) {
        for (int i = 0; i < 5; i++) {
          final String label = "Field " + (i + 1);
          final String classIdSuffix = "" + i;
          fields.addLast(new AbstractStringField() {

            @Override
            protected String getConfiguredLabel() {
              return label;
            }

            @Override
            public String classId() {
              return ConfigurationUtility.getAnnotatedClassIdWithFallback(getClass()) + ID_CONCAT_SYMBOL + classIdSuffix;
            }
          });
        }
        super.injectFieldsInternal(fields);
      }
    }

    @Order(30)
    public class GroupBoxPropertiesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 4;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Group Box Properties";
      }

      @Override
      protected boolean getConfiguredExpandable() {
        return true;
      }

      @Order(10)
      public class BorderVisibleField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Border Visible";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getExampleBox().isBorderVisible());
        }

        @Override
        protected void execChangedValue() {
          getExampleBox().setBorderVisible(isChecked());
        }
      }

      @Order(20)
      public class ScrollableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Scrollable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getExampleBox().isScrollable().getBooleanValue());
        }

        @Override
        protected void execChangedValue() {
          getExampleBox().setScrollable(isChecked());
        }
      }

      @Order(40)
      public class ExpandableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Expandable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getExampleBox().isExpandable());
        }

        @Override
        protected void execChangedValue() {
          getExampleBox().setExpandable(isChecked());
        }
      }

      @Order(50)
      public class ExpandedField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Expanded";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getExampleBox().isExpanded());
          getExampleBox().addPropertyChangeListener(IGroupBox.PROP_EXPANDED, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
              ExpandedField.this.setValue(getExampleBox().isExpanded());
            }
          });
        }

        @Override
        protected void execChangedValue() {
          getExampleBox().setExpanded(isChecked());
        }
      }
    }

    @Order(40)
    @ClassId("72146909-e06c-4ccf-af13-5df3f691817b")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected boolean getConfiguredExpanded() {
        return true;
      }

      @Override
      protected void execInitField() {
        setFormField(getForm().getFieldByClass(ExampleBox.class));
      }
    }

    @Order(60)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
