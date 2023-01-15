// This is a generated file. Not intended for manual editing.
package com.en_circle.slt.plugin.lisp.impl;

import com.en_circle.slt.plugin.lisp.psi.LispCompoundSymbol;
import com.en_circle.slt.plugin.lisp.psi.LispSymbol;
import com.en_circle.slt.plugin.lisp.psi.LispVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class LispCompoundSymbolImpl extends ASTWrapperPsiElement implements LispCompoundSymbol {

  public LispCompoundSymbolImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LispVisitor visitor) {
    visitor.visitCompoundSymbol(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LispVisitor) accept((LispVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LispSymbol getSymbol() {
    return findNotNullChildByClass(LispSymbol.class);
  }

}
