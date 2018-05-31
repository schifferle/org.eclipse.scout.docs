jswidgets.CustomTileFilter = function(model) {
  model = model || {};
  this.text = null;
  this.setText(model.text);
};

jswidgets.CustomTileFilter.prototype.setText = function(text) {
  this.text = text || '';
  this.text = this.text.toLowerCase();
};

jswidgets.CustomTileFilter.prototype.accept = function(tile) {
  var label = tile.label || '';
  var filterText = label.trim().toLowerCase();
  return filterText.indexOf(this.text) > -1;
};
