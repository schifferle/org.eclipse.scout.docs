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
jswidgets.PopupVerticalAlignLookupCall = function() {
  jswidgets.PopupVerticalAlignLookupCall.parent.call(this);
};
scout.inherits(jswidgets.PopupVerticalAlignLookupCall, scout.StaticLookupCall);

jswidgets.PopupVerticalAlignLookupCall.prototype._data = function() {
  return jswidgets.PopupVerticalAlignLookupCall.DATA;
};

jswidgets.PopupVerticalAlignLookupCall.DATA = [
  [scout.Popup.VerticalAlign.TOP, 'top'],
  [scout.Popup.VerticalAlign.TOPEDGE, 'topedge'],
  [scout.Popup.VerticalAlign.CENTER, 'center'],
  [scout.Popup.VerticalAlign.BOTTOM, 'bottom'],
  [scout.Popup.VerticalAlign.BOTTOMEDGE, 'bottomedge'],
];
