package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public final class WidgetsClientConfigProperties {

  public static class GitUrlConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.url";
    }

    @Override
    public String description() {
      return "Base URL that points to the source code of the widgets app on github.";
    }

    @Override
    public String getDefaultValue() {
      return "https://github.com/bsi-software/org.eclipse.scout.docs/blob";
    }
  }

  public static class GitBranchConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.branch";
    }

    @Override
    public String description() {
      return "Git branch that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "releases/8.0.x";
    }
  }

  public static class GitFolderConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.folder";
    }

    @Override
    public String description() {
      return "Git subfolder that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "code/widgets";
    }
  }

  public static class GitSourceConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.source";
    }

    @Override
    public String description() {
      return "Git source folder that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "src/main/java";
    }
  }
}
