/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.GroupBoxForm = function() {
  jswidgets.GroupBoxForm.parent.call(this);
};
scout.inherits(jswidgets.GroupBoxForm, scout.Form);

jswidgets.GroupBoxForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.GroupBoxForm');
};

jswidgets.GroupBoxForm.prototype._init = function(model) {
  jswidgets.GroupBoxForm.parent.prototype._init.call(this, model);

  var groupBox = this.widget('DetailBox');

  // GroupBox Properties tab
  this.widget('GroupBoxPropertiesBox').setField(groupBox);
  this.widget('Actions.AddFieldBox').setField(groupBox);
  this.widget('Actions.DeleteFieldBox').setField(groupBox);
  this.widget('FormFieldPropertiesBox').setField(groupBox);

  var bodyLayoutConfigBox = this.widget('BodyLayoutConfigBox');
  bodyLayoutConfigBox.getLayoutConfig = function() {
    return this.field.bodyLayoutConfig;
  };
  bodyLayoutConfigBox.updateLayoutConfig = function(layoutConfig) {
    this.field.setBodyLayoutConfig(layoutConfig);
  };
  bodyLayoutConfigBox.setField(groupBox);
  this.widget('GridDataBox').setField(groupBox);

  // ContentProperties tab
  var targetField = this.widget('TargetField');
  targetField.setLookupCall(new jswidgets.FormFieldLookupCall(groupBox));
  targetField.on('propertyChange', this._onTargetPropertyChange.bind(this));
  targetField.setValue(groupBox.fields[0]);

  this._onTargetPropertyChange({
    propertyName: 'value',
    newValue: targetField.value
  });
};

jswidgets.GroupBoxForm.prototype._onTargetPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    var targetField = event.newValue;

    var contentFormFieldPropertiesBox = this.widget('ContentFormFieldPropertiesBox');
    contentFormFieldPropertiesBox.setField(targetField);
    contentFormFieldPropertiesBox.setEnabled(!!targetField);

    var contentGridDataBox = this.widget('ContentGridDataBox');
    contentGridDataBox.setField(targetField);
    contentGridDataBox.setEnabled(!!targetField);
  }
};
