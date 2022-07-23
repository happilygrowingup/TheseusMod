package com.growuphappily.theseus.core;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HeightTransformer implements ITransformer<ClassNode>, Opcodes {
    @Nonnull
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        for (MethodNode mn: input.methods) {
            if(mn.name.equals("getMaxBuildHeight") || mn.name.equals("isOutsideBuildHeight")){
                for (AbstractInsnNode insn: mn.instructions) {
                    if(insn.getOpcode() == SIPUSH){
                        mn.instructions.set(insn, new IntInsnNode(SIPUSH, 1024));
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
        return new HashSet<>(Arrays.asList(Target.targetClass("net.minecraft.world.IBlockReader"), Target.targetClass("net.minecraft.world.World")));
    }
}
