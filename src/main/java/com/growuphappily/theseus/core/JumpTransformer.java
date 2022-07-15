package com.growuphappily.theseus.core;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class JumpTransformer implements ITransformer<ClassNode>, Opcodes {
    @Nonnull
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        for(MethodNode mn: input.methods){
            if(Objects.equals(mn.name, "func_225607_a_") || Objects.equals(mn.name, "tick")){
                for(AbstractInsnNode insn: mn.instructions){
                    if(insn.getOpcode() == RETURN){
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 0));
                        list.add(new MethodInsnNode(INVOKESTATIC, "com/growuphappily/theseus/network/PacketJumpState", "onJump", "(Lnet/minecraft/util/MovementInputFromOptions;)V"));
                        mn.instructions.insertBefore(insn, list);
                    }
                }
            }
        }
        return input;
    }

    @Nonnull
    @Override
    public TransformerVoteResult castVote(ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @Nonnull
    @Override
    public Set<Target> targets() {
        return new HashSet<>(Arrays.asList(Target.targetClass("net.minecraft.util.MovementInputFromOptions")));
    }
}
