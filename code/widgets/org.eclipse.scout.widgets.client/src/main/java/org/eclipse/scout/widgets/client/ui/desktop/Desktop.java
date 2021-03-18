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
package org.eclipse.scout.widgets.client.ui.desktop;

import java.security.AccessController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.context.PropertyMap;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.AdvancedWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.LayoutWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.PagesOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.SimpleWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

public class Desktop extends AbstractDesktop {

  private IForm m_benchModeForm = null;
  private final FormListener m_benchModeFormListener = new P_BenchModeFormListener();

  @Override
  protected String getConfiguredDisplayStyle() {
    return resolveDesktopStyle();
  }

  /**
   * Returns the 'desktopStyle' provided as part of the URL, or the default style otherwise.<br/>
   * E.g. http://[host:port]/?desktopStyle=BENCH to start in bench mode.
   */
  protected String resolveDesktopStyle() {
    String desktopStyle = PropertyMap.CURRENT.get().get("desktopStyle");
    if (desktopStyle != null) {
      return desktopStyle.toLowerCase();
    }
    else {
      return DISPLAY_STYLE_DEFAULT;
    }
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<>();
    outlines.add(SimpleWidgetsOutline.class);
    outlines.add(AdvancedWidgetsOutline.class);
    outlines.add(LayoutWidgetsOutline.class);
    outlines.add(PagesOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ScoutWidgetsDemoApp");
  }

  @Override
  protected String getConfiguredLogoId() {
    return "application_logo";
  }

  @Override
  protected void execDefaultView() {
    if (DISPLAY_STYLE_BENCH.equals(getDisplayStyle())) {
      // "bench-only" desktop
      if (m_benchModeForm != null) {
        m_benchModeForm.activate();
      }
      else {
        IForm benchForm = null;
        for (IDesktopExtension ext : getDesktopExtensions()) {
          if (ext instanceof IBenchFormProvider) {
            benchForm = ((IBenchFormProvider) ext).provideBenchForm();
          }
        }
        if (benchForm == null) {
          benchForm = new StringFieldForm();
        }
        benchForm.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
        benchForm.start();
        setBenchModeForm(benchForm);
      }
    }
    else {
      // default desktop
      IOutline firstOutline = CollectionUtility.firstElement(getAvailableOutlines());
      if (firstOutline != null) {
        activateOutline(firstOutline);
      }
    }
  }

  protected void setBenchModeForm(IForm form) {
    // Uninstall
    if (m_benchModeForm != null) {
      m_benchModeForm.removeFormListener(m_benchModeFormListener);
      m_benchModeForm = null;
    }
    // Install
    if (form != null) {
      m_benchModeForm = form;
      m_benchModeForm.addFormListener(m_benchModeFormListener);
    }
  }

  protected IForm getBenchModeForm() {
    return m_benchModeForm;
  }

  @Order(100)
  public class UserProfileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredText() {
      Subject subject = Subject.getSubject(AccessController.getContext());
      Principal firstPrincipal = CollectionUtility.firstElement(subject.getPrincipals());
      return StringUtility.uppercaseFirst(firstPrincipal.getName());
    }

    @Order(1000)
    @ClassId("e60844f6-a1d5-4534-9e94-a1f445f2049f")
    public class OptionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Options");
      }

      @Override
      protected void execAction() {
        OptionsForm form = new OptionsForm();
        form.startNew();
      }
    }

    @Order(2000)
    @ClassId("80f3b2a6-3be9-4c49-b840-99cb3b52cf92")
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AboutMenu");
      }

      @Override
      protected void execAction() {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }

    @Order(3000)
    @ClassId("4937c432-adc1-481a-8ca8-62d64da7b0a0")
    public class LogoutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logout");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }

  }

  @Order(10)
  public class SimpleWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public SimpleWidgetsOutlineViewButton() {
      super(Desktop.this, SimpleWidgetsOutline.class);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F4;
    }
  }

  @Order(20)
  public class AdvancedWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public AdvancedWidgetsOutlineViewButton() {
      super(Desktop.this, AdvancedWidgetsOutline.class);
    }
  }

  @Order(30)
  public class LayoutWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public LayoutWidgetsOutlineViewButton() {
      super(Desktop.this, LayoutWidgetsOutline.class);
    }
  }

  @Order(40)
  public class PagesOutlineViewButton extends AbstractOutlineViewButton {
    public PagesOutlineViewButton() {
      super(Desktop.this, PagesOutline.class);
    }
  }

  protected class P_BenchModeFormListener implements FormListener {

    @Override
    public void formChanged(FormEvent e) {
      if (e.getType() == FormEvent.TYPE_CLOSED) {
        setBenchModeForm(null);
      }
    }
  }
}
