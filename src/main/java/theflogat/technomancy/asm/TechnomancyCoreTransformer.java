package theflogat.technomancy.asm;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.common.Loader;
import net.minecraft.launchwrapper.IClassTransformer;

public class TechnomancyCoreTransformer implements IClassTransformer {

    @Override
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if ("powercrystals.minefactoryreloaded.MineFactoryReloadedClient".equals(name) && Loader.isModLoaded("Thaumcraft")) {
            return patchRenderWorldLast(basicClass);
        }

        return basicClass;
    }

    private static byte[] patchRenderWorldLast(final byte[] origCode) {
        final ClassReader cr = new ClassReader(origCode);

        final ClassNode classNode = new ClassNode();
        cr.accept(classNode, 0);

        for (final MethodNode methodNode : classNode.methods) {
            if ("renderWorldLast".equals(methodNode.name) && "(Lnet/minecraftforge/client/event/RenderWorldLastEvent;)V".equals(methodNode.desc)) {
                final Iterator<AbstractInsnNode> insnNodes = methodNode.instructions.iterator();
                boolean foundIf = false;
                boolean foundJump = false;
                LabelNode label = new LabelNode();
                final LabelNode retNode = new LabelNode();
                while (insnNodes.hasNext() && !foundIf) {
                    final AbstractInsnNode insn = insnNodes.next();

                    if (insn.getOpcode() == Opcodes.IFNULL && !foundJump) {
                        foundJump = true;
                        ((JumpInsnNode) insn).label = retNode;
                    }
                    if (insn.getOpcode() == Opcodes.RETURN) {
                        foundIf = true;
                        final InsnList endList = new InsnList();
                        endList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        endList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/player/EntityPlayer", "field_71071_by", "Lnet/minecraft/entity/player/InventoryPlayer;"));
                        endList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/player/InventoryPlayer", "func_70448_g", "()Lnet/minecraft/item/ItemStack;", false));
                        endList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false));
                        endList.add(new TypeInsnNode(Opcodes.INSTANCEOF, "theflogat/technomancy/common/items/thaumcraft/ItemTechnoturgeScepter"));
                        endList.add(new JumpInsnNode(Opcodes.IFNE, label));
                        endList.add(retNode);
                        methodNode.instructions.insertBefore(insn, endList);
                        methodNode.instructions.insert(insn, label);
                    }
                }
            }
        }

        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(cw);

        return cw.toByteArray();
    }

}
