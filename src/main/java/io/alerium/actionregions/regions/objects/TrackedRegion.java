package io.alerium.actionregions.regions.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class TrackedRegion {
    
    private final String id;
    private final RegionAction enterAction;
    private final RegionAction leaveAction;
    
}
