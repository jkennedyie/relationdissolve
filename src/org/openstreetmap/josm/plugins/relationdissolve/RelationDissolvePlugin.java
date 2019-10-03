// License: GPL. For details, see LICENSE file.

/**
 * A JOSM plugin to dissolve then select the members of selected relations.
 * Simplifies creating a new boundary from existing openstreetmap boundaries.
 */

package org.openstreetmap.josm.plugins.relationdissolve;

import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.relationdissolve.actions.RelationDissolveAction;


/**
 * Relation Dissolve plugin
 * @author John Kennedy
 */
public class RelationDissolvePlugin extends Plugin {

	/**
	 * Action that dissolves and selects members of relations selected in the Relations window
	 */
	private final RelationDissolveAction relationListAction = new RelationDissolveAction();

	/**
	 * Constructs a new {@code RelationDissolvePlugin}
	 * @param info Plugin information
	 */
	public RelationDissolvePlugin(PluginInformation info) {
		super(info);
	}

	@Override
	public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
		if (newFrame != null) {
			// Initialize dialog action only after the main frame is created
			newFrame.relationListDialog.getPopupMenuHandler().addSeparator();
			newFrame.relationListDialog.getPopupMenuHandler().addAction(relationListAction);
		} else if (oldFrame != null) {
			// Remove listener from previous frame to avoid memory leaks
			if (oldFrame.relationListDialog != null) {
				oldFrame.relationListDialog.getPopupMenuHandler().removeAction(relationListAction);
			}
		}
	}
}
