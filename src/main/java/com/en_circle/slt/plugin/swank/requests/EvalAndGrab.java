package com.en_circle.slt.plugin.swank.requests;

import com.en_circle.slt.plugin.lisp.lisp.LispContainer;
import com.en_circle.slt.plugin.lisp.lisp.LispElement;
import com.en_circle.slt.plugin.lisp.lisp.LispString;
import com.en_circle.slt.plugin.lisp.lisp.LispSymbol;
import com.en_circle.slt.plugin.swank.SlimeRequest;
import com.en_circle.slt.plugin.swank.SwankPacket;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

public class EvalAndGrab extends SlimeRequest {

    public static SlimeRequest eval(String code, String module, boolean parse, Callback callback) {
        return new EvalAndGrab(code, module, parse, callback);
    }

    public static SlimeRequest eval(String code, boolean parse, Callback callback) {
        return new EvalAndGrab(code, "cl-user", parse, callback);
    }

    protected final Callback callback;
    protected final String module;
    protected final String code;
    protected final boolean parse;

    protected EvalAndGrab(String code, String module, boolean parse, Callback callback) {
        this.callback = callback;
        this.module = module;
        this.code = code;
        this.parse = parse;
    }

    public void processReply(LispContainer data, Function<String, List<LispElement>> parser) {
        if (isOk(data)) {
            LispContainer list = (LispContainer) data.getItems().get(1);
            String returnedStdout = ((LispString) list.getItems().get(0)).getValue();
            String returnedForm = ((LispString) list.getItems().get(1)).getValue();
            List<LispElement> parsed = null;
            if (parse && parser != null) {
                returnedForm = StringUtils.replace(returnedForm, "\\\"", "\"");
                returnedForm = StringUtils.replace(returnedForm, "\\\\", "\\");
                parsed = parser.apply(returnedForm);
            }
            callback.onResult(returnedForm, returnedStdout, parsed);
        }
    }

    private boolean isOk(LispContainer data) {
        return data.getItems().size() > 0 &&
                data.getItems().get(0) instanceof LispSymbol &&
                ":ok".equals(((LispSymbol) data.getItems().get(0)).getValue());
    }

    @Override
    public SwankPacket createPacket(BigInteger requestId) {
        return SwankPacket.swankEvalAndGrab(code, module, requestId);
    }

    public interface Callback {
        void onResult(String result, String stdout, List<LispElement> parsed);
    }

}
