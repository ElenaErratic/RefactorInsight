package org.jetbrains.research.refactorinsight.folding.handlers;

import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.refactorinsight.adapters.RefactoringType;
import org.jetbrains.research.refactorinsight.data.RefactoringInfo;
import org.jetbrains.research.refactorinsight.folding.FoldingDescriptor;
import org.jetbrains.research.refactorinsight.utils.Utils;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InlineOperationFoldingHandler implements FoldingHandler {
  @NotNull
  @Override
  public List<FoldingDescriptor> getFolds(@NotNull RefactoringInfo info, @NotNull PsiFile file, boolean isBefore) {
    String path = info.getMidPath() != null ? info.getMidPath() : info.getLeftPath();
    if (!isBefore || !file.getVirtualFile().getPath().endsWith(path)) {
      return Collections.emptyList();
    }
    String hintText = "Inlined to " + Utils.functionSimpleName(info.getNameAfter());
    if (info.getType() == RefactoringType.MOVE_AND_INLINE_OPERATION) {
      hintText += " in " + info.getRightPath().substring(info.getRightPath().lastIndexOf(File.separatorChar) + 1);
    }
    FoldingDescriptor descriptor = info.getFoldingDescriptorMid();
    descriptor.addHintText(hintText);
    return Collections.singletonList(descriptor);
  }

  @NotNull
  @Override
  public FoldingDescriptor uniteFolds(@NotNull List<FoldingDescriptor> folds) {
    List<String> destinations = folds.stream()
        .map(folding -> folding.hintText().substring("Inlined to ".length()))
        .distinct()
        .collect(Collectors.toList());
    String hintText = "Inlined to ";
    if (destinations.size() < 4) {
      hintText += String.join(", ", destinations);
    } else {
      hintText += destinations.size() + " methods";
    }
    FoldingDescriptor descriptor = folds.get(0);
    descriptor.addHintText(hintText);
    return descriptor;
  }
}
