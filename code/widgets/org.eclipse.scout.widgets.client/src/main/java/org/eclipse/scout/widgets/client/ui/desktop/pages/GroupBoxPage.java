package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxVerticalScrollingForm;

@FormPageParent
public class GroupBoxPage extends FormPage {

  public GroupBoxPage() {
    super(GroupBoxForm.class);
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new FormPage(GroupBoxVerticalScrollingForm.class));
    pageList.add(new FormPage(GroupBoxHorizontalScrollingForm.class));
  }
}
