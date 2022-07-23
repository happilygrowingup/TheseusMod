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

public class ChunkHeightTransformer implements ITransformer<ClassNode>, Opcodes {

    @Nonnull
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        for (MethodNode mn: input.methods) {
            if(mn.name.equals("<init>")){
                for (AbstractInsnNode insn: mn.instructions) {
                    if(insn.getOpcode() == BIPUSH){
                        mn.instructions.set(insn, new IntInsnNode(BIPUSH, 64));
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
        return new HashSet<>(Arrays.asList(Target.targetClass("net.minecraft.world.chunk.Chunk"), Target.targetClass("net.minecraft.world.server.ChunkHolder")));
    }
}
