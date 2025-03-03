package com.en_circle.slt.plugin.actions;

import com.en_circle.slt.plugin.SltBundle;
import com.en_circle.slt.plugin.SltCommonLispFileType;
import com.en_circle.slt.plugin.services.lisp.LispEnvironmentService;
import com.en_circle.slt.plugin.swank.requests.Eval;
import com.en_circle.slt.plugin.swank.requests.EvalFromVirtualFile;
import com.en_circle.slt.plugin.swank.requests.LoadFile;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.FileContentUtilCore;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class EvalActionBase extends AnAction {
    private static final Logger log = LoggerFactory.getLogger(EvalActionBase.class);

    @Override
    public void update(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(false);

        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor != null && event.getProject() != null) {
            PsiFile file = PsiDocumentManager.getInstance(Objects.requireNonNull(editor.getProject())).getPsiFile(editor.getDocument());
            if (file != null && SltCommonLispFileType.INSTANCE.equals(file.getFileType())) {
                event.getPresentation().setEnabledAndVisible(true);
            }
        }
    }

    protected void evaluate(Project project, String buffer, String packageName, Runnable callback) {
        try {
            LispEnvironmentService.getInstance(project).sendToLisp(Eval.eval(buffer, packageName, result -> callback.run()), true);
        } catch (Exception e) {
            log.warn(SltBundle.message("slt.error.sbclstart"), e);
            Messages.showErrorDialog(project, e.getMessage(), SltBundle.message("slt.ui.errors.sbcl.start"));
        }
    }

    protected void evaluateRegion(Project project, String buffer, String packageName, String filename, int bufferPosition, int lineno, int charno, Runnable callback) {
        try {
            LispEnvironmentService.getInstance(project).sendToLisp(EvalFromVirtualFile
                    .eval(buffer, filename, bufferPosition, lineno, charno, packageName, result -> callback.run()), true);
        } catch (Exception e) {
            log.warn(SltBundle.message("slt.error.sbclstart"), e);
            Messages.showErrorDialog(project, e.getMessage(), SltBundle.message("slt.ui.errors.sbcl.start"));
        }
    }

    protected void evaluateFile(Project project, String filename, VirtualFile virtualFile) {
        try {
            LispEnvironmentService.getInstance(project).sendToLisp(LoadFile.loadFile(filename), true);
            FileContentUtilCore.reparseFiles(virtualFile);
        } catch (Exception e) {
            log.warn(SltBundle.message("slt.error.sbclstart"), e);
            Messages.showErrorDialog(project, e.getMessage(), SltBundle.message("slt.ui.errors.sbcl.start"));
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
