package org.jetbrains.research.refactorinsight.ui.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.refactorinsight.data.Group;

public enum DisplayedGroup {
  METHOD,
  CLASS,
  VARIABLE,
  ABSTRACT,
  PACKAGE;

  @NotNull
  public static DisplayedGroup fromInternalGroup(@NotNull Group group) {
    switch (group) {
      case METHOD:
        return METHOD;
      case ATTRIBUTE:
      case VARIABLE:
        return VARIABLE;
      case INTERFACE:
      case CLASS:
        return CLASS;
      case ABSTRACT:
        return ABSTRACT;
      case PACKAGE:
        return PACKAGE;
      default:
        throw new IllegalStateException("Unexpected value: " + group);
    }
  }
}
