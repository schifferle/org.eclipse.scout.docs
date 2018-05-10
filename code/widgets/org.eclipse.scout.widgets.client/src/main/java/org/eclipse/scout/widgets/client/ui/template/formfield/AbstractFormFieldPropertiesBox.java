package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractProposalField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield2.AbstractSmartField2;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IMultiStatus;
import org.eclipse.scout.rt.platform.status.MultiStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.EnabledField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.LabelVisibleField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.LoadingField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.MandatoryField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.StatusVisibleField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.FlagsBox.VisibleField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.DisabledStyleField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.ErrorStatusField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.LabelField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.LabelPositionField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.LabelWidthInPixelField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.PropertiesBox.TooltipTextField;

public class AbstractFormFieldPropertiesBox extends AbstractGroupBox {

  private IFormField m_formField;

  public IFormField getFormField() {
    return m_formField;
  }

  public void setFormField(IFormField formField) {
    m_formField = formField;
  }

  @Override
  protected String getConfiguredLabel() {
    return "Form Field Properties";
  }

  @Override
  protected boolean getConfiguredExpandable() {
    return true;
  }

  @Override
  protected int getConfiguredGridColumnCount() {
    return 3;
  }

  public DisabledStyleField getDisabledStyleField() {
    return getFieldByClass(DisabledStyleField.class);
  }

  public EnabledField getEnabledField() {
    return getFieldByClass(EnabledField.class);
  }

  public ErrorStatusField getErrorStatusField() {
    return getFieldByClass(ErrorStatusField.class);
  }

  public LabelField getLabelField() {
    return getFieldByClass(LabelField.class);
  }

  public LabelPositionField getLabelPositionField() {
    return getFieldByClass(LabelPositionField.class);
  }

  public LabelVisibleField getLabelVisibleField() {
    return getFieldByClass(LabelVisibleField.class);
  }

  public LabelWidthInPixelField getLabelWidthInPixelField() {
    return getFieldByClass(LabelWidthInPixelField.class);
  }

  public LoadingField getLoadingField() {
    return getFieldByClass(LoadingField.class);
  }

  public MandatoryField getMandatoryField() {
    return getFieldByClass(MandatoryField.class);
  }

  public StatusVisibleField getStatusVisibleField() {
    return getFieldByClass(StatusVisibleField.class);
  }

  public TooltipTextField getTooltipTextField() {
    return getFieldByClass(TooltipTextField.class);
  }

  public VisibleField getVisibleField() {
    return getFieldByClass(VisibleField.class);
  }

  protected MultiStatus createStatus(Integer severity) {
    if (severity == null) {
      return null;
    }

    Status status = new Status(TEXTS.get("FormFieldStatusMessage", getSeverityName(severity)), severity);
    MultiStatus multiStatus = new MultiStatus();
    multiStatus.add(status);
    return multiStatus;
  }

  protected String getSeverityName(int severity) {
    switch (severity) {
      case Status.OK: {
        return "OK";
      }
      case Status.INFO: {
        return "INFO";
      }
      case Status.WARNING: {
        return "WARNING";
      }
      case Status.ERROR: {
        return "ERROR";
      }
      default:
        return "undefined";
    }
  }

  @Order(1000)
  public class FlagsBox extends AbstractGroupBox {

    protected boolean m_labelVisible;

    @Override
    protected void initConfig() {
      m_labelVisible = getConfiguredCheckBoxLabelsVisible();
      super.initConfig();
    }

    protected boolean getConfiguredCheckBoxLabelsVisible() {
      return false;
    }

    @Override
    protected int getConfiguredGridW() {
      return 1;
    }

    @Override
    protected int getConfiguredWidthInPixel() {
      return 200;
    }

    @Override
    protected double getConfiguredGridWeightX() {
      return 0;
    }

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Override
    protected boolean getConfiguredBorderVisible() {
      return false;
    }

    @Order(1000)
    @ClassId("c136fb40-c06e-4c76-8020-68e5d21b5f90")
    public class EnabledField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Enabled";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setEnabled(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isEnabled());
      }
    }

    @Order(2000)
    @ClassId("90559bc5-6112-44eb-8f4d-f1ffe3480a7d")
    public class LabelVisibleField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Label Visible";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setLabelVisible(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isLabelVisible());
      }
    }

    @Order(3000)
    @ClassId("fb06a141-d26d-4913-a9a5-0226cfad7fc2")
    public class LoadingField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Loading";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setLoading(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isLoading());
      }
    }

    @Order(4000)
    @ClassId("d91da096-33f4-4ccd-bfb1-74b1100690ee")
    public class MandatoryField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Mandatory";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setMandatory(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isMandatory());
      }
    }

    @Order(5000)
    @ClassId("cc327ed7-cc2f-4ca7-8775-8a2b4408dcb3")
    public class StatusVisibleField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Status Visible";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setStatusVisible(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isStatusVisible());
      }
    }

    @Order(6000)
    @ClassId("0341856c-de3a-4b18-b495-8fe667d3fc46")
    public class VisibleField extends AbstractBooleanField {

      @Override
      protected String getConfiguredLabel() {
        return "Visible";
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return m_labelVisible;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setVisible(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().isVisible());
      }
    }
  }

  @Order(2000)
  public class PropertiesBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridW() {
      return 2;
    }

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Override
    protected boolean getConfiguredBorderVisible() {
      return false;
    }

    @Order(10_000)
    @ClassId("cf9716c3-7d52-44f4-96e6-f80791b67e46")
    public class DisabledStyleField extends AbstractSmartField2<Integer> {

      @Override
      protected String getConfiguredLabel() {
        return "Disabled Style";
      }

      @Override
      protected String getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_DROPDOWN;
      }

      @Override
      protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
        return DisabledStyleLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setDisabledStyle(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getDisabledStyle());
      }
    }

    @Order(11_000)
    @ClassId("39042fa7-8106-4794-92ff-f0fa47519f6a")
    public class ErrorStatusField extends AbstractSmartField2<Integer> {

      @Override
      protected String getConfiguredLabel() {
        return "Error Status";
      }

      @Override
      protected String getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_DROPDOWN;
      }

      @Override
      protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
        return StatusSeverityLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        MultiStatus errorStatus = createStatus(getValue());
        if (errorStatus == null) {
          getFormField().clearErrorStatus();
        }
        else {
          getFormField().setErrorStatus(errorStatus);
        }
      }

      @Override
      protected void execInitField() {
        IMultiStatus errorStatus = getFormField().getErrorStatus();
        if (errorStatus != null) {
          setValue(errorStatus.getSeverity());
        }
      }
    }

    @Order(12_000)
    @ClassId("20bc9efe-b332-4a82-9965-2f0fb1a7ad11")
    public class LabelField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return "Label";
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getLabel());
      }

      @Override
      protected void execChangedValue() {
        getFormField().setLabel(getValue());
        getMenuByClass(ResetLabelMenu.class).setVisible(this.isSaveNeeded());
      }

      @Order(10)
      public class ResetLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Restore Initial Value";
        }

        @Override
        protected void execAction() {
          LabelField.this.resetValue();
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }

      @Order(20)
      public class SetLongLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Lorem Ipsum (Long)";
        }

        @Override
        protected void execAction() {
          LabelField.this.setValue(""
              + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
              + "sed diam nonumy eirmod tempor invidunt ut labore et dolore "
              + "magna aliquyam erat, sed diam voluptua. "
              + "At vero eos et accusam et justo duo dolores et ea rebum. "
              + "Stet clita kasd gubergren, no sea takimata sanctus est.");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }

      @Order(30)
      public class SetShortLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Lorem Ipsum (Short)";
        }

        @Override
        protected void execAction() {
          LabelField.this.setValue("Lorem ipsum dolor sit amet");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }
    }

    @Order(13_000)
    @ClassId("2fbba681-751c-4094-a440-eb35d731c619")
    public class LabelPositionField extends AbstractSmartField2<Byte> {

      @Override
      protected String getConfiguredLabel() {
        return "Label Position";
      }

      @Override
      protected String getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_DROPDOWN;
      }

      @Override
      protected Class<? extends ILookupCall<Byte>> getConfiguredLookupCall() {
        return LabelPositionLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setLabelPosition(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getLabelPosition());
      }
    }

    @Order(14_000)
    @ClassId("bc6fa04b-17bc-4ebf-a1df-4ddc1b99d5fe")
    public class LabelWidthInPixelField extends AbstractProposalField<String> {

      @Override
      protected String getConfiguredLabel() {
        return "Label Width in Pixel";
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return LabelWidthInPixelLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        int width = IFormField.LABEL_WIDTH_DEFAULT;
        String value = getValue();

        if (value != null) {
          List<? extends ILookupRow<String>> rows = getLookupCall().getDataByAll();
          boolean found = false;
          for (ILookupRow<String> row : rows) {
            if (row.getText().equals(value)) {
              width = Integer.parseInt(row.getKey());
              found = true;
              break;
            }
          }
          if (!found) {
            width = Integer.parseInt(value);
          }
        }

        getFormField().setLabelWidthInPixel(width);
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getLabelWidthInPixel() + "");
      }
    }

    @Order(15_000)
    @ClassId("16ccbbb3-e965-4634-b257-26a115365b29")
    public class StatusPositionField extends AbstractSmartField2<String> {

      @Override
      protected String getConfiguredLabel() {
        return "Status Position";
      }

      @Override
      protected String getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_DROPDOWN;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return StatusPositionLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        getFormField().setStatusPosition(getValue());
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getStatusPosition());
      }
    }

    @Order(16_000)
    @ClassId("2ff7fdfc-fe32-48ec-8007-9e948e4554cc")
    public class TooltipTextField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return "Tooltip Text";
      }

      @Override
      protected void execInitField() {
        setValue(getFormField().getTooltipText());
      }

      @Override
      protected void execChangedValue() {
        getFormField().setTooltipText(getValue());
        getMenuByClass(ResetLabelMenu.class).setVisible(this.isSaveNeeded());
      }

      @Order(10)
      public class ResetLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Restore Initial Value";
        }

        @Override
        protected void execAction() {
          TooltipTextField.this.resetValue();
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }

      @Order(20)
      public class SetLongLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Lorem Ipsum (Long)";
        }

        @Override
        protected void execAction() {
          TooltipTextField.this.setValue(""
              + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
              + "sed diam nonumy eirmod tempor invidunt ut labore et dolore "
              + "magna aliquyam erat, sed diam voluptua. "
              + "At vero eos et accusam et justo duo dolores et ea rebum. "
              + "Stet clita kasd gubergren, no sea takimata sanctus est.");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }

      @Order(30)
      public class SetShortLabelMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Lorem Ipsum (Short)";
        }

        @Override
        protected void execAction() {
          TooltipTextField.this.setValue("Lorem ipsum dolor sit amet");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
        }
      }
    }
  }
}
