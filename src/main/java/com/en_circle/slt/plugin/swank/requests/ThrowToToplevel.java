package com.en_circle.slt.plugin.swank.requests;

import com.en_circle.slt.plugin.swank.SlimeRequest;
import com.en_circle.slt.plugin.swank.SwankPacket;

import java.math.BigInteger;

public class ThrowToToplevel extends SlimeRequest {

    public static SlimeRequest threadId(BigInteger threadId) {
        return new ThrowToToplevel(threadId);
    }

    private final BigInteger threadId;

    public ThrowToToplevel(BigInteger threadId) {
        this.threadId = threadId;
    }

    @Override
    public SwankPacket createPacket(BigInteger requestId) {
        return SwankPacket.throwToToplevel(threadId, requestId);
    }
}
