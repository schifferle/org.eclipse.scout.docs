/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client;

import java.util.List;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.outlines.ContactsOutline;
import org.eclipse.scout.contacts.client.outlines.SearchOutline;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bookmark.menu.AbstractBookmarkMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;

public class Desktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.<Class<? extends IOutline>> arrayList(ContactsOutline.class, SearchOutline.class);
  }

  @Override
  protected void execGuiAttached() throws ProcessingException {
    super.execGuiAttached();
    setVisibleOutline();
  }

  @Override
  protected void setDesktopStyle(DesktopStyle desktopStyle) {
    super.setDesktopStyle(desktopStyle);
  }

  protected void setVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline instanceof ContactsOutline) {
        setOutline(outline);
        break;
      }
    }
  }

  @Order(1000)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("File");
    }

    @Order(1000.0)
    public class ExitMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Exit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        ClientSessionProvider.currentSession(ClientSession.class).stop();
      }
    }
  }

  @Order(2000)
  public class BookmarkMenu extends AbstractBookmarkMenu {
    public BookmarkMenu() {
      super(Desktop.this);
    }
  }

  @Order(3000)
  public class HelpMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Help");
    }

    @Order(500.0)
    public class OptionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Options");
      }

      @Override
      protected void execAction() throws ProcessingException {
        OptionsForm form = new OptionsForm();
        form.startNew();
      }
    }

    @Order(1000)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("About");
      }

      @Override
      protected void execAction() throws ProcessingException {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }
  }

  @Order(10.0)
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F5;
    }

    @Override
    protected void execAction() throws ProcessingException {
      if (getOutline() != null) {
        IPage<?> page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }

  @Order(1000.0)
  public class ContactsOutlineViewButton extends AbstractOutlineViewButton {

    public ContactsOutlineViewButton() {
      this(ContactsOutline.class);
    }

    protected ContactsOutlineViewButton(Class<? extends ContactsOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.MENU;
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Person;
    }
  }

  @Order(2000.0)
  public class SearchOutlineViewButton extends AbstractOutlineViewButton {

    public SearchOutlineViewButton() {
      this(SearchOutline.class);
    }

    protected SearchOutlineViewButton(Class<? extends SearchOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.Search;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F3;
    }
  }
}