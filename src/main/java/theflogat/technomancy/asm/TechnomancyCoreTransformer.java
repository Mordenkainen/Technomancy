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

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

public class TechnomancyCoreTransformer implements IClassTransformer {

	static boolean isDeobfEnvironment;
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		isDeobfEnvironment = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
				
		if(name.equals("powercrystals.minefactoryreloaded.MineFactoryReloadedClient")) {
			byte[] newCode = patchRenderWorldLast(basicClass, isDeobfEnvironment);
			return newCode;
		}
		
		return basicClass;
	}

	private static byte[] patchRenderWorldLast(byte[] origCode, boolean isDeobfEnvironment) {
		ClassReader cr = new ClassReader(origCode);

		ClassNode classNode=new ClassNode();
		cr.accept(classNode, 0);

		for(MethodNode methodNode : classNode.methods) {
			if(methodNode.name.equals("renderWorldLast") && methodNode.desc.equals("(Lnet/minecraftforge/client/event/RenderWorldLastEvent;)V"))	{
				Iterator<AbstractInsnNode> insnNodes=methodNode.instructions.iterator();
				boolean foundIf = false;
				boolean foundJump = false;
				LabelNode label = new LabelNode();
				LabelNode retNode = new LabelNode();
				while(insnNodes.hasNext() && !foundIf) {
					AbstractInsnNode insn=insnNodes.next();
					
					if(insn.getOpcode()==Opcodes.IFNULL && !foundJump) {
						foundJump = true;
						((JumpInsnNode)insn).label = retNode;
					}
					if(insn.getOpcode()==Opcodes.RETURN) {
						foundIf = true;
						InsnList endList=new InsnList();
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
		
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		classNode.accept(cw);
		
		return cw.toByteArray();
	}

}
