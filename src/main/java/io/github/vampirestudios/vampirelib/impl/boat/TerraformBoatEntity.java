package io.github.vampirestudios.vampirelib.impl.boat;

import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType;
import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/**
 * A {@linkplain Boat boat entity} that stores a {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatEntity extends Boat {
	private static final String BOAT_KEY = "TerraformBoat";
	private static final EntityDataAccessor<TerraformBoatType> TERRAFORM_BOAT = SynchedEntityData.defineId(TerraformBoatEntity.class, TerraformBoatTrackedData.HANDLER);

	public TerraformBoatEntity(EntityType<? extends TerraformBoatEntity> type, Level world) {
		super(type, world);
	}

	public TerraformBoatEntity(Level world) {
		this(TerraformBoatInitializer.BOAT, world);
	}

	public TerraformBoatEntity(Level world, double x, double y, double z) {
		this(TerraformBoatInitializer.BOAT, world);

		this.setPos(x, y, z);
		this.xOld = x;
		this.yOld = y;
		this.zOld = z;
	}

	public TerraformBoatType getTerraformBoat() {
		return this.entityData.get(TERRAFORM_BOAT);
	}

	public void setTerraformBoat(TerraformBoatType boat) {
		this.entityData.set(TERRAFORM_BOAT, boat);
	}

	private boolean hasValidTerraformBoat() {
		return this.getTerraformBoat() != null;
	}

	@Override
	public Item getDropItem() {
		return this.getTerraformBoat().getItem();
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return this.hasValidTerraformBoat() && super.shouldRender(cameraX, cameraY, cameraZ);
	}

	@Override
	public void tick() {
		if (this.hasValidTerraformBoat()) {
			super.tick();
		} else {
			this.discard();
		}
	}

	@Override
	public void setType(Boat.Type type) {}

	@Override
	public Boat.Type getBoatType() {
		return Boat.Type.OAK;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.set(TERRAFORM_BOAT, null);
	}

	// Serialization
	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		ResourceLocation id = ResourceLocation.tryParse(nbt.getString(BOAT_KEY));
		if (id != null) {
			TerraformBoatType boat = TerraformBoatTypeRegistry.INSTANCE.get(id);
			if (boat != null) {
				this.setTerraformBoat(boat);
			}
		}

		if (!this.hasValidTerraformBoat()) {
			this.discard();
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);

		ResourceLocation boatId = TerraformBoatTypeRegistry.INSTANCE.getKey(this.getTerraformBoat());
		if (boatId != null) {
			nbt.putString(BOAT_KEY, boatId.toString());
		}
	}
}
