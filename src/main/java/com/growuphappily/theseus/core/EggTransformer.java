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

public class EggTransformer implements ITransformer<ClassNode> ,Opcodes{
    @Nonnull
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        for (MethodNode node: input.methods) {
            if(Objects.equals(node.name, "<init>")){
                for (AbstractInsnNode insn: node.instructions) {
                    if(insn.getOpcode() == RETURN){
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 0));
                        list.add(new InsnNode(ICONST_1));
                        list.add(new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/screen/MainMenuScreen", "minceraftEasterEgg", "Z"));
                        node.instructions.insertBefore(insn, list);
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
        return new HashSet<Target>(Arrays.asList(Target.targetClass("net.minecraft.client.gui.screen.MainMenuScreen")));
    }
}
