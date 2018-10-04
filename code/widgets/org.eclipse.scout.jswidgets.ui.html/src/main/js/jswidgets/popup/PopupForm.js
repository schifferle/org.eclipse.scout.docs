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
jswidgets.PopupForm = function() {
  jswidgets.PopupForm.parent.call(this);
};
scout.inherits(jswidgets.PopupForm, scout.Form);

jswidgets.PopupForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.PopupForm');
};

jswidgets.PopupForm.prototype._init = function(model) {
  jswidgets.PopupForm.parent.prototype._init.call(this, model);

  var dummyPopup = scout.create('Popup', {
    parent: this
  });
  this.widget('OpenPopupButton').on('click', this._onOpenPopupButtonClick.bind(this));

  var openingDirectionXField = this.widget('OpeningDirectionXField');
  openingDirectionXField.setValue(dummyPopup.openingDirectionX);

  var openingDirectionYField = this.widget('OpeningDirectionYField');
  openingDirectionYField.setValue(dummyPopup.openingDirectionY);

  var trimWidthField = this.widget('TrimWidthField');
  trimWidthField.setValue(dummyPopup.trimWidth);

  var trimHeightField = this.widget('TrimHeightField');
  trimHeightField.setValue(dummyPopup.trimHeight);
  dummyPopup.close();
};

jswidgets.PopupForm.prototype._onOpenPopupButtonClick = function(model) {
  var $anchor;
  if (this.widget('UseButtonAsAnchorField').value) {
    $anchor = this.widget('OpenPopupButton').$field;
  }
  var anchorBounds;
  var anchorBoundsRaw = this.widget('AnchorBoundsField').value;
  if (anchorBoundsRaw) {
    anchorBoundsRaw = anchorBoundsRaw.split(',');
    anchorBounds = new scout.Rectangle(Number(anchorBoundsRaw[0]), Number(anchorBoundsRaw[1]), Number(anchorBoundsRaw[2]), Number(anchorBoundsRaw[3]));
  }
  var popup = scout.create('WidgetPopup', {
    parent: this,
    $anchor: $anchor,
    anchorBounds: anchorBounds,
    openingDirectionX: this.widget('OpeningDirectionXField').value,
    openingDirectionY: this.widget('OpeningDirectionYField').value,
    trimWidth: this.widget('TrimWidthField').value,
    trimHeight: this.widget('TrimHeightField').value,
    cssClass: 'popup-form-popup',
    widget: {
      objectType: "LabelField",
      labelVisible: false,
      statusVisible: false,
      wrapText: true,
      htmlEnabled: true,
      value: '<h2>Hi, I\'m a popup!</h2>' +
          '<p>This widget popup contains a label field to display some text.</p>' +
          '<p>Instead of a label field you can use any other widget you like, the popup will act as wrapper for your widget.</p>' +
          '<p>The popup will be as big as its content, so you probably need to define its size, e.g. using the min- and max-width CSS properties.</p>'
    }
  });
  this.widget('EventsTab').setField(popup);
  this.widget('WidgetActionsBox').setField(popup);
  popup.open();
};
