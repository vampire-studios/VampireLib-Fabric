/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib;

import com.mojang.serialization.Lifecycle;
import io.github.vampirestudios.vampirelib.modules.FeatureManager;
import io.github.vampirestudios.vampirelib.utils.Rands;
import io.github.vampirestudios.vampirelib.utils.registry.BlockChiseler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class VampireLib implements ModInitializer {

    public static final Registry<FeatureManager> FEATURE_MANAGERS = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier("vampirelib:feature_managers")), Lifecycle.stable());
    public static String MOD_ID = "vampirelib";
    public static String MOD_NAME = "VampireLib";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static String MOD_VERSION = "3.0.0+build.2 for 21w18a";
    public static final Map<String, Identifier> DEV_UUID = new HashMap<String, Identifier>(){{
        //CatCore
        this.put("5bb676ef-ecee-4258-ae1f-53e163839585", new Identifier(MOD_ID, "textures/misc/cat_cape.png"));
        //OliviaTheVampire
        this.put("b344687b-ec74-479a-9540-1aa8ccb13e92", new Identifier(MOD_ID, "textures/misc/1_16.png"));
        //Tridentflayer
        this.put("ab6a4eb9-b02a-4909-a71e-65a8dbe5ee54", new Identifier(MOD_ID, "textures/misc/turtle.png"));
        //SkyladySelena
        this.put("641cb212-8d44-4a2c-aa47-8396918fa336", new Identifier(MOD_ID, "textures/misc/selena_cape.png"));
        //AgentM
        this.put("b5701a2d-9593-4224-a61e-cabb3887cfc7", new Identifier(MOD_ID, "textures/misc/agent_m_cape_v1.png"));
        //Avaier
        this.put("978fdd1b-c1d6-4483-9287-eb796833c52f", new Identifier(MOD_ID, "textures/misc/avaier_dinosaur.png"));
        //Toothless
//        this.put("978fdd1b-c1d6-4483-9287-eb796833c52f", new Identifier(MOD_ID, "textures/misc/avaier_dinosaur.png"));
        //Tirdul
        this.put("dcb06728-b65a-44ca-97eb-2d92d647f70f", new Identifier(MOD_ID, "textures/misc/turtle.png"));
        //Phoenixfirewingz
        this.put("97404a0d-02be-4d80-b1b8-10e2d8c748ea", new Identifier(MOD_ID, "textures/misc/1_16.png"));
    }};

    @Override
    public void onInitialize() {
        LOGGER.info((Rands.chance(15) ? "Your are" : (Rands.chance(15) ? "You're" : "You are")) + " running " + MOD_NAME + " v" + MOD_VERSION);
        BlockChiseler.setup();
    }

}