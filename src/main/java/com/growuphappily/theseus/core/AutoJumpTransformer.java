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

public class AutoJumpTransformer implements ITransformer<ClassNode>, Opcodes {
    @Nonnull
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        for(MethodNode mn : input.methods){
            if(Objects.equals(mn.name, "func_175161_p")){
                for(AbstractInsnNode insn : mn.instructions) {
                    if(insn.getOpcode() == RETURN) {
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 0));
                        list.add(new FieldInsnNode(GETSTATIC, "com/growuphappily/theseus/network/PacketJumpState", "jumpState", "Z"));
                        list.add(new FieldInsnNode(PUTFIELD, "net/minecraft/client/entity/player/ClientPlayerEntity", "field_189811_cr", "Z"));
                        mn.instructions.insertBefore(insn, list);
                    }
                }
            }
            if(Objects.equals(mn.name, "sendPosition")){
                for(AbstractInsnNode insn : mn.instructions) {
                    if(insn.getOpcode() == RETURN) {
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 0));
                        list.add(new FieldInsnNode(GETSTATIC, "com/growuphappily/theseus/network/PacketJumpState", "jumpState", "Z"));
                        list.add(new FieldInsnNode(PUTFIELD, "net/minecraft/client/entity/player/ClientPlayerEntity", "autoJumpEnabled", "Z"));
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
        return new HashSet<>(Arrays.asList(Target.targetClass("net.minecraft.client.entity.player.ClientPlayerEntity")));
    }
}
