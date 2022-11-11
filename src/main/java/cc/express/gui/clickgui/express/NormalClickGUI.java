package cc.express.gui.clickgui.express;

import cc.express.gui.fontrender.FontManager;
import cc.express.modules.Category;
import cc.express.modules.Module;
import cc.express.modules.ModuleManager;
import cc.express.modules.values.Mode;
import cc.express.modules.values.Numbers;
import cc.express.modules.values.Option;
import cc.express.modules.values.Value;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.io.IOException;

import net.minecraft.util.*;


/**
 * @author Fadouse
 */
public class NormalClickGUI extends GuiScreen
{
	public static Category currentModuleType;
	public static Module currentModule;
	public static float startX;
	public static float startY;
	public int moduleStart = 0;
	public int valueStart = 0;
	boolean previousmouse = true;
	boolean Rpreviousmouse = true;
	boolean mouse;
	public Opacity opacity = new Opacity(0);
	public int opacityx = 255;
	public float moveX = 0.0F;
	public float moveY = 0.0F;

	public float lastPercent;
	public float percent;
	public float percent2;
	public float lastPercent2;

	public float outro;
	public float lastOutro;

	public int mouseWheel;

	public int mouseX;
	public int mouseY;

	static {
		currentModuleType = Category.Combat;
		currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0
				? ModuleManager.getModulesInType(currentModuleType).get(0)
				: null;
		startX = 100.0F;
		startY = 100.0F;
	}
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		lastPercent = percent;
		lastPercent2 = percent2;
		if (percent > .98) {
			percent += ((.98 - percent) / (1.45f)) - 0.001;
		}
		if (percent <= .98) {
			if (percent2 < 1) {
				percent2 += ((1 - percent2) / (2.8f)) + 0.002;
			}
		}
		if (this.isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY)
				&& Mouse.isButtonDown(0)) {
			if (this.moveX == 0.0F && this.moveY == 0.0F) {
				this.moveX = (float) mouseX - startX;
				this.moveY = (float) mouseY - startY;
			} else {
				startX = (float) mouseX - this.moveX;
				startY = (float) mouseY - this.moveY;
			}

			this.previousmouse = true;
		} else if (this.moveX != 0.0F || this.moveY != 0.0F) {
			this.moveX = 0.0F;
			this.moveY = 0.0F;
		}
		this.opacity.interpolate((float) this.opacityx);
		RenderUtil.drawRect(startX, startY, startX + 60.0F, startY + 320.0F,
				(new Color(40, 40, 40, (int) this.opacity.getOpacity())).getRGB());
		RenderUtil.drawRect(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F,
				(new Color(31, 31, 31, (int) this.opacity.getOpacity())).getRGB());
		RenderUtil.drawRect(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F,
				(new Color(40, 40, 40, (int) this.opacity.getOpacity())).getRGB());

//		FontManager.F20.drawStringWithShadow(Client.USERNAME,startX + (60 - FontLoaders.Baloo20.getStringWidth(Client.USERNAME))/ 2,startY + 300,new Color(255,255,255,255).getRGB());
//		RenderUtil.drawRainbowRect(startX, startY, startX + 419, startY + 1);

		int m;
		for (m = 0; m < Category.values().length; ++m) {
			Category[] mY = Category.values();
			if (mY[m] != currentModuleType) {

				RenderUtil.drawCircle(startX + 28F, startY + 38.0F + (float) (m * 42),16,new Color(80, 80, 80,160).getRGB());
				if (mY[m].toString() == "Combat") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Combat.png"),(int)startX + 20, (int)startY + 30+ m * 42 , 16, 16);
				}if (mY[m].toString() == "Movement") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Move.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Player") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Player.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Render") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Render.png"),(int)startX + 20, (int)startY + 30+ m * 42 , 16, 16);
				}if (mY[m].toString() == "World") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/World.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Misc") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Exploit.png"),(int)startX + 20, (int)startY + 30+ m * 42 , 16, 16);
				}
			} else {
				RenderUtil.drawCircle(startX + 28F, startY + 38.0F + (float) (m * 42),16,new Color(61, 141, 255,160).getRGB());
				if (mY[m].toString() == "Combat") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Combat.png"),(int)startX + 20, (int)startY + 30+ m * 42 , 16, 16);
				}if (mY[m].toString() == "Movement") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Move.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Player") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Player.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Render") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Render.png"),(int)startX + 20, (int)startY + 30+ m * 42 , 16, 16);
				}if (mY[m].toString() == "World") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/World.png"),(int)startX + 20, (int)startY + 30 + m * 42, 16, 16);
				}if (mY[m].toString() == "Misc") {
					RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/Exploit.png"),(int)startX + 20, (int)startY + 30 + m * 42 , 16, 16);
				}
			}
			try {
				if (this.isCategoryHovered(startX + 10.0F, startY + 20.0F + (float) (m * 42), startX + 56.0F, startY + 46.0F + (float) (m * 42), mouseX, mouseY) && Mouse.isButtonDown(0)) {
					currentModuleType = mY[m];
					currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0
							? (Module) ModuleManager.getModulesInType(currentModuleType).get(0)
							: null;
					this.moduleStart = 0;
				}
			} catch (Exception var23) {
				System.err.println(var23);
			}
		}
		mouseWheel = Mouse.getDWheel();
		if (this.isCategoryHovered(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, mouseX, mouseY)) {
			if (mouseWheel < 0 && this.moduleStart < ModuleManager.getModulesInType(currentModuleType).size()- 1) {
				++this.moduleStart;
			}

			if (mouseWheel > 0 && this.moduleStart > 0) {
				--this.moduleStart;
			}
		}

		if (this.isCategoryHovered(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, mouseX, mouseY)) {
			if (mouseWheel < 0 && this.valueStart < currentModule.getValues().size()) {
				++this.valueStart;
			}

			if (mouseWheel > 0 && this.valueStart > 0) {
				--this.valueStart;
			}
		}

		double sli1 = (294.5/(ModuleManager.getModulesInType(currentModuleType).size()))*4d;

		//Draw Sli1
		if(ModuleManager.getModulesInType(currentModuleType).size() > 2)
			RenderUtil.drawRect(startX + 199f,(float) (startY + 25.5d + ((294.5 - sli1) / (ModuleManager.getModulesInType(currentModuleType).size()-1d)*(moduleStart))), startX + 201f,(float) (startY + 25.5d + ((294.5 - sli1)/(ModuleManager.getModulesInType(currentModuleType).size()-1d)*(moduleStart)) + sli1), new Color(160, 160, 160).getRGB());
		else
			RenderUtil.drawRect(0,0,0,0,Color.white.getRGB());
		//Draw Type
		FontManager.F16.drawString(
				currentModule == null ? currentModuleType.toString() : currentModuleType.toString(),
				startX + 70.0F, startY + 12.5F, (new Color(248, 248, 248)).getRGB());
		RenderUtil.drawRoundedRect(startX + 60.0F, startY+2 , startX + 200.0F, startY + 25.5F, new Color(255,40,68).getRGB(), new Color(40,40,40,100).getRGB());

		//Draw Modules!
		if (currentModule != null) {
			float var24 = startY + 30.0F;
			int i;
			FontManager.F20.drawString(currentModule.name,startX+210,startY + 12.5f ,new Color(255,255,255,255).getRGB() );
			for (i = 0; i < ModuleManager.getModulesInType(currentModuleType).size(); ++i) {
				Module value = (Module) ModuleManager.getModulesInType(currentModuleType).get(i);

				if (var24 > startY + 300.0F) {
					break;
				}
				RenderUtil.drawRoundRect(startX+195, var24 , startX+65, var24+20, new Color(40,40,40,255).getRGB());
				if (i >= this.moduleStart) {


					if (!value.isEnabled()) {
//						RenderUtil.drawRoundedRect((float)(startX + 67.0F), (float)(var24+7),(float)(startX + 80.0F), (float)(var24 + 13) ,new Color(255, 42, 68).getRGB(),new Color(255, 42, 68).getRGB());
//						RenderUtil.drawRoundedRect(startX + 66f, (float)(var24+6), (float)(startX + 72), (float)(var24+14), new Color(160, 160, 160).getRGB(), new Color(180, 180, 180).getRGB());
						RenderUtil.drawCircle(startX + 74f, (float)(var24+10), 2, new Color(120, 120, 120).getRGB());
					} else {
//						RenderUtil.drawRoundedRect((float)(startX + 67.0F), (float)(var24+7),(float)(startX + 80.0F), (float)(var24 + 13) ,new Color(70, 255, 115).getRGB(),new Color(70, 255, 115).getRGB());
//						RenderUtil.drawRoundedRect(startX + 75f, (float)(var24+6), (float)(startX + 81), (float)(var24+14), new Color(160, 160, 160).getRGB(), new Color(180, 180, 180).getRGB());
						RenderUtil.drawCircle(startX + 74f, (float)(var24+10), 2, new Color(81, 161, 255).getRGB());
					}

					//Button XY
					if (this.isSettingsButtonHovered(startX + 67F, var24+2,
							startX + 183.0F,
							var24 + 16.0F + (float) FontManager.F20.getHeight(), mouseX, mouseY)) {
						if (!this.previousmouse && Mouse.isButtonDown(0)) {
							if (value.isEnabled()) {
								value.setState(false);
							} else {
								value.setState(true);
							}

							this.previousmouse = true;
						}

						if (!this.previousmouse && Mouse.isButtonDown(1)) {
							this.previousmouse = true;

						}
						FontManager.F16.drawString(value.getName(), startX + 86.0F, var24 + 8.0F,
								(new Color(120, 120, 120, (int) this.opacity.getOpacity())).getRGB());

					}else
						FontManager.F16.drawString(value.getName(), startX + 86.0F, var24 + 8.0F,
								(new Color(248, 248, 248, (int) this.opacity.getOpacity())).getRGB());

					if (!Mouse.isButtonDown(0)) {
						this.previousmouse = false;
					}


					if (this.isSettingsButtonHovered(startX + 90.0F, var24,
							startX + 100.0F + (float) FontManager.F20.getStringWidth(value.getName()),
							var24 + 8.0F + (float) FontManager.F20.getHeight(), mouseX, mouseY)
							&& Mouse.isButtonDown(1)) {
						currentModule = value;
						this.valueStart = 0;
					}

					FontManager.F20.drawString(":",startX+185,var24 + 10 - FontManager.F20.getHeight()/2,new Color(255,255,255,255).getRGB() );
					var24 += 25.0F;
				}

			}

			var24 = startY + 30.0F;
			for (i = 0; i < currentModule.getValues().size() && var24 <= startY + 300.0F; ++i) {
				if (i >= this.valueStart) {
					Value var25 = (Value) currentModule.getValues().get(i);
					float x;

					if (var25 instanceof Numbers) {
						x = startX + 295.0F;
						double current = (double) (62.0F
								* (((Number) var25.getValue()).floatValue()
								- ((Numbers) var25).getMin().floatValue())
								/ (((Numbers) var25).getMin().floatValue()
								- ((Numbers) var25).getMin().floatValue()));

						RenderUtil.drawRect(x - 4.0F, var24+1, (float) ((double) x + 69.0D), var24 + 4.0F,
								(new Color(50, 50, 50, (int) this.opacity.getOpacity())).getRGB());
						RenderUtil.drawBorderedRect(x + 75.0F, var24-3, (float) ((double) x + 100.0D), var24 + 9.0F,1,
								(new Color(85, 85, 85, (int) this.opacity.getOpacity())).getRGB(),(new Color(55, 55, 55, (int) this.opacity.getOpacity())).getRGB());

						RenderUtil.drawRect(x - 4.0F, var24+1, (float) ((double) x + current + 0.5), var24+4.0F,
								(new Color(61, 141, 255, (int) this.opacity.getOpacity())).getRGB());
						RenderUtil.drawRect((float) ((double) x + current + 2.0D), var24,
								(float) ((double) x + current + 7.0D), var24 + 5.0F,
								(new Color(100, 100, 100, (int) this.opacity.getOpacity())).getRGB());
						FontManager.F18.drawStringWithShadow(var25.getDisplayName(),
								startX + 210.0F, var24, -1);
//						FontLoaders.kiona18.drawCenteredStringWithShadow(""+ ((((Number) var25.getValue()).doubleValue() - (double) (((Number) var25.getValue()).intValue())) == 0.0d? ((Number) var25.getValue()).intValue():((Number) var25.getValue()).doubleValue()),x + 83+14.5f,var24,-1);
						if (!Mouse.isButtonDown(0)) {
							this.previousmouse = false;
						}
						if((((Number) var25.getValue()).doubleValue() - (double) (((Number) var25.getValue()).intValue())) == 0.0d)
							FontManager.F18.drawCenteredStringWithShadow(String.valueOf(((Number) var25.getValue()).intValue()),x + 67+20f,var24,-1);
						else
							FontManager.F18.drawCenteredStringWithShadow(String.valueOf(((Number) var25.getValue())),x + 67+20f,var24,-1);
//						System.out.println(((((Number) var25.getValue()).doubleValue() - (double) (((Number) var25.getValue()).intValue())) == 0.0d? ((Number) var25.getValue()).intValue():((Number) var25.getValue()).doubleValue()));

						if (this.isButtonHovered(x, var24 - 2.0F, x + 100.0F, var24 + 7.0F, mouseX, mouseY)
								&& Mouse.isButtonDown(0)) {
							if (!this.previousmouse && Mouse.isButtonDown(0)) {
								current = ((Numbers) var25).getMin().doubleValue();
								double max = ((Numbers) var25).getMax().doubleValue();
								double inc = ((Numbers) var25).getInc().doubleValue();
								double valAbs = (double) mouseX - ((double) x + 1.0D);
								double perc = valAbs / 64.0D;
								perc = Math.min(Math.max(0.0D, perc), 1.0D);
								double valRel = (max - current) * perc;
								double val = current + valRel;
								val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
								((Numbers) var25).setValue(Double.valueOf(val));
							}

							if (!Mouse.isButtonDown(0)) {
								this.previousmouse = false;
							}
						}

						var24 += 20.0F;
					}

					if (var25 instanceof Option) {
						x = startX + 300.0F;
						FontManager.F20.drawStringWithShadow(var25.getDisplayName(), startX + 210.0F, var24, -1);
//						RenderUtil.drawRect(x + 56.0F, var24, x + 76.0F, var24 + 1.0F,
//								(new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
//						RenderUtil.drawRect(x + 56.0F, var24 + 8.0F, x + 76.0F, var24 + 9.0F,
//								(new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
//						RenderUtil.drawRect(x + 56.0F, var24, x + 57.0F, var24 + 9.0F,
//								(new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
//						RenderUtil.drawRect(x + 77.0F, var24, x + 76.0F, var24 + 9.0F,
//								(new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
						RenderUtil.drawRoundedRect(x + 56.0F, var24,x + 76.0F,var24 + 8.0F, 3,new Color(60, 60, 60, (int) this.opacity.getOpacity()).getRGB());
						if (((Boolean) var25.getValue()).booleanValue()) {
//							RenderUtil.drawRect(x + 66.5F, var24 + 2.0F, x + 75.0F, var24 + 7.0F,
//									(new Color(64, 255, 15, (int) this.opacity.getOpacity())).getRGB());
							RenderUtil.drawCircle(x + 70.5,var24 + 4,4,new Color(61,141,255).getRGB());
						} else {
							RenderUtil.drawCircle(x + 62,var24 + 4,4,new Color(120,120,120).getRGB());
						}

						if (this.isCheckBoxHovered(x + 56.0F, var24, x + 76.0F, var24 + 9.0F, mouseX, mouseY)) {
							if (!this.previousmouse && Mouse.isButtonDown(0)) {
								this.previousmouse = true;
								this.mouse = true;
							}

							if (this.mouse) {
								var25.setValue(Boolean.valueOf(!((Boolean) var25.getValue()).booleanValue()));
								this.mouse = false;
							}
						}

						if (!Mouse.isButtonDown(0)) {
							this.previousmouse = false;
						}

						var24 += 20.0F;
					}

					if (var25 instanceof Mode) {
						x = startX + 300.0F;
						Enum var26 = (Enum) ((Mode) var25).getValue();

						int next_str = var26.ordinal() - 1 < 0 ? ((Mode) var25).getModes().length - 1
								: var26.ordinal() - 1;
						int next_str_2 = var26.ordinal() + 1 >= ((Mode) var25).getModes().length ? 0
								: var26.ordinal() + 1;

						FontManager.F18.drawStringWithShadow(var25.getDisplayName(), startX + 210.0F, var24+2, -1);
						RenderUtil.drawRect(x - 10.0F, var24 - 5.0F, x + 95.0F, var24 + 15.0F,
								(new Color(56, 56, 56, (int) this.opacity.getOpacity())).getRGB());
						RenderUtil.drawBorderRect((double) (x - 10.0F), (double) (var24 - 5.0F), (double) (x + 95.0F),
								(double) (var24 + 15.0F),
								(new Color(120, 120, 120, (int) this.opacity.getOpacity())).getRGB(), 2.0D);
						for(int i1=0; i1<2; i1++){
							FontManager.F18
									.drawStringWithShadow(((Mode) var25).getModes()[next_str].name().substring(((Mode) var25).getModes()[next_str].name().length() - 2 + i1,((Mode) var25).getModes()[next_str].name().length() + i1 - 1), x - 3 + i1 * FontManager.F18.getStringWidth(((Mode) var25).getModes()[next_str].name().substring(((Mode) var25).getModes()[next_str].name().length() - 3 + i1,((Mode) var25).getModes()[next_str].name().length() + i1 - 2))  ,var24+2, new Color(255,255,255,80/(3 - (i1+1))).getRGB());
							FontManager.F18
									.drawStringWithShadow(((Mode) var25).getModes()[next_str_2].name().substring(i1,i1+1), x + 79 + ((i1>0) ? (i1 * FontManager.F18.getStringWidth(((Mode) var25).getModes()[next_str_2].name().substring(i1-1,i1))):0),var24+2,  new Color(255,255,255,80/(i1+1)).getRGB());
						}


						FontManager.F18
								.drawStringWithShadow(((Mode) var25).getName(), x + 42.5F - (float) (FontManager.F18.getStringWidth(((Mode) var25).getName()) / 2),
										var24+2, -1);
						if (this.isStringHovered(x, var24 - 5.0F, x + 100.0F, var24 + 15.0F, mouseX, mouseY)) {
							if(!previousmouse) {
								if (Mouse.isButtonDown(0)) {
									int next = var26.ordinal() + 1 >= ((Mode) var25).getModes().length ? 0
											: var26.ordinal() + 1;
									var25.setValue(((Mode) var25).getModes()[next]);
									this.previousmouse = true;
								}
							}
								if (Mouse.isButtonDown(1) && !Rpreviousmouse) {
									int next = var26.ordinal() - 1 < 0 ? ((Mode) var25).getModes().length - 1
											: var26.ordinal() - 1;
									var25.setValue(((Mode) var25).getModes()[next]);
									this.Rpreviousmouse = true;
								}


							if (!Mouse.isButtonDown(0)) {
								this.previousmouse = false;
							}
							if (!Mouse.isButtonDown(1)) {
								this.Rpreviousmouse = false;
							}
						}

						var24 += 25.0F;
					}
				}
			}
		}
	}

	@Override
	public void initGui() {
//		if (this.mc.theWorld != null) {
//			this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
//		}
	}

	@Override
	public void onGuiClosed() {
//		this.opacity.setOpacity(0.0F);
//		mc.entityRenderer.switchUseShader();
	}


	public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
	}

	public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
	}

	public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
	}

	public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
	}

	public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
	}

	public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
	}
}
