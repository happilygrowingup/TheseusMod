package com.growuphappily.theseus.core;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;

import javax.annotation.Nonnull;
import java.util.*;

public class EggService implements ITransformationService {
    @Nonnull
    @Override
    public String name() {
        return "EggService";
    }

    @Override
    public void initialize(IEnvironment environment) {

    }

    @Override
    public void beginScanning(IEnvironment environment) {

    }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {

    }

    @Nonnull
    @Override
    public List<ITransformer> transformers() {
        return Arrays.asList(new EggTransformer());
    }
}
