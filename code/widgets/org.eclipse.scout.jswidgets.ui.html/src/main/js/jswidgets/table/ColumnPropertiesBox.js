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
jswidgets.ColumnPropertiesBox = function() {
  jswidgets.ColumnPropertiesBox.parent.call(this);
  this.column = null;
};
scout.inherits(jswidgets.ColumnPropertiesBox, scout.GroupBox);

jswidgets.ColumnPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ColumnPropertiesBox');
};

jswidgets.ColumnPropertiesBox.prototype._init = function(model) {
  jswidgets.ColumnPropertiesBox.parent.prototype._init.call(this, model);

  this._setColumn(this.column);
};

jswidgets.ColumnPropertiesBox.prototype.setColumn = function(column) {
  this.setProperty('column', column);
};

jswidgets.ColumnPropertiesBox.prototype._setColumn = function(column) {
  this._setProperty('column', column);
  if (!this.column) {
    return;
  }
  var autoOptimizeWidthField = this.widget('AutoOptimizeWidthField');
  autoOptimizeWidthField.setValue(this.column.autoOptimizeWidth);
  autoOptimizeWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var autoOptimizeMaxWidthField = this.widget('AutoOptimizeMaxWidthField');
  autoOptimizeMaxWidthField.setValue(this.column.autoOptimizeMaxWidth);
  autoOptimizeMaxWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var editableField = this.widget('EditableField');
  editableField.setValue(this.column.editable);
  editableField.on('propertyChange', this._onPropertyChange.bind(this));

  var modifiableField = this.widget('ModifiableField');
  modifiableField.setValue(this.column.modifiable);
  modifiableField.on('propertyChange', this._onPropertyChange.bind(this));

  var removableField = this.widget('RemovableField');
  removableField.setValue(this.column.removable);
  removableField.on('propertyChange', this._onPropertyChange.bind(this));

  var fixedWidthField = this.widget('FixedWidthField');
  fixedWidthField.setValue(this.column.fixedWidth);
  fixedWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var fixedPositionField = this.widget('FixedPositionField');
  fixedPositionField.setValue(this.column.fixedPosition);
  fixedPositionField.on('propertyChange', this._onPropertyChange.bind(this));

  var groupedField = this.widget('GroupedField');
  groupedField.setValue(this.column.grouped);
  groupedField.on('propertyChange', this._onPropertyChange.bind(this));

  var headerMenuEnabledField = this.widget('HeaderMenuEnabledField');
  headerMenuEnabledField.setValue(this.column.headerMenuEnabled);
  headerMenuEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var headerHtmlEnabledField = this.widget('HeaderHtmlEnabledField');
  headerHtmlEnabledField.setValue(this.column.headerHtmlEnabled);
  headerHtmlEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var htmlEnabledField = this.widget('HtmlEnabledField');
  htmlEnabledField.setValue(this.column.htmlEnabled);
  htmlEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var mandatoryField = this.widget('MandatoryField');
  mandatoryField.setValue(this.column.mandatory);
  mandatoryField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortActiveField = this.widget('SortActiveField');
  sortActiveField.setValue(this.column.sortActive);
  sortActiveField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortAscendingField = this.widget('SortAscendingField');
  sortAscendingField.setValue(this.column.sortAscending);
  sortAscendingField.on('propertyChange', this._onPropertyChange.bind(this));

  var textWrapField = this.widget('TextWrapField');
  textWrapField.setValue(this.column.textWrap);
  textWrapField.on('propertyChange', this._onPropertyChange.bind(this));

  var displayableField = this.widget('DisplayableField');
  displayableField.setValue(this.column.displayable);
  displayableField.on('propertyChange', this._onPropertyChange.bind(this));

  var visibleField = this.widget('VisibleField');
  visibleField.setValue(this.column.visible);
  visibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var cssClassField = this.widget('CssClassField');
  cssClassField.setValue(this.column.cssClass);
  cssClassField.on('propertyChange', this._onPropertyChange.bind(this));

  var horizontalAlignmentField = this.widget('HorizontalAlignmentField');
  horizontalAlignmentField.setValue(this.column.horizontalAlignment);
  horizontalAlignmentField.on('propertyChange', this._onPropertyChange.bind(this));

  var textField = this.widget('TextField');
  textField.setValue(this.column.text);
  textField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortIndexField = this.widget('SortIndexField');
  sortIndexField.setValue(this.column.sortIndex);
  sortIndexField.on('propertyChange', this._onPropertyChange.bind(this));

  var widthField = this.widget('WidthField');
  widthField.setValue(this.column.width);
  widthField.on('propertyChange', this._onPropertyChange.bind(this));

  var minWidthField = this.widget('MinWidthField');
  minWidthField.setValue(this.column.minWidth);
  minWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var maxLengthField = this.widget('MaxLengthField');
  maxLengthField.setValue(this.column.maxLength);
  maxLengthField.on('propertyChange', this._onPropertyChange.bind(this));

  column.table.on('sort', this._onSort.bind(this));
  column.table.on('group', this._onGroup.bind(this));
  column.table.on('columnResized', this._onColumnResized.bind(this));
};

jswidgets.ColumnPropertiesBox.prototype._onColumnResized = function(data){
  if (this.column === data.column) {
    var widthField = this.widget('WidthField');
    widthField.setValue(this.column.width);
  }
};

jswidgets.ColumnPropertiesBox.prototype._onSort = function(data) {
  if (this.column === data.column) {
    var sortActiveField = this.widget('SortActiveField');
    sortActiveField.setValue(this.column.sortActive);
    var sortAscendingField = this.widget('SortAscendingField');
    sortAscendingField.setValue(this.column.sortAscending);
    var sortIndexField = this.widget('SortIndexField');
    sortIndexField.setValue(this.column.sortIndex);
  }
};

jswidgets.ColumnPropertiesBox.prototype._onGroup = function(data) {
  if (this.column === data.column) {
    var groupedField = this.widget('GroupedField');
    groupedField.setValue(this.column.grouped);
  }
};

jswidgets.ColumnPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'AutoOptimizeWidthField') {
    this.column.setAutoOptimizeWidth(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'VisibleField') {
    this.column.setVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'DisplayableField') {
    this.column.setDisplayable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'CssClassField') {
    this.column.setCssClass(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'EditableField') {
    this.column.setEditable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'MandatoryField') {
    this.column.setMandatory(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'WidthField') {
    this.column.setWidth(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'MaxLengthField') {
    this.column.setMaxLength(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'TextWrapField') {
    this.column.setTextWrap(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'HorizontalAlignmentField') {
    var hAlign = event.newValue;
    if (hAlign < 0) {
      hAlign = -1;
    } else if (hAlign > 0) {
      hAlign = 1;
    }
    this.column.setHorizontalAlignment(hAlign);
  } else if (event.propertyName === 'value' && event.source.id === 'FixedWidthField') {
    this.column.fixedWidth = event.newValue;
    this.column.table.invalidateLayoutTree();
  } else if (event.propertyName === 'value' && event.source.id === 'FixedPositionField') {
    this.column.fixedPosition = event.newValue;
  } else if (event.propertyName === 'value' && event.source.id === 'HeaderMenuEnabledField') {
    this.column.headerMenuEnabled = event.newValue;
  }
};
