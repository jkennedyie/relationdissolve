// License: GPL. For details, see LICENSE file.

/**
 * Action for JOSM Relations Window to dissolve then select members
 * of selected relations.
 */

package org.openstreetmap.josm.plugins.relationdissolve.actions;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openstreetmap.josm.actions.IPrimitiveAction;
import org.openstreetmap.josm.actions.relation.AbstractRelationAction;
import org.openstreetmap.josm.data.osm.IPrimitive;
import org.openstreetmap.josm.data.osm.IRelation;
import org.openstreetmap.josm.data.osm.IRelationMember;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 * Action to dissolve then select members of selected relations.
 * @author John Kennedy
 */
public class RelationDissolveAction extends AbstractRelationAction implements IPrimitiveAction {

	/**
	 * Constructs a new {@code RelationDissolveAction}.
	 */
	public RelationDissolveAction() {
		putValue(SHORT_DESCRIPTION, tr("Dissolve then select the members of all selected relations"));
		putValue(NAME, tr("Select dissolved members"));
		new ImageProvider("RelationDissolve").getResource().attachImageIcon(this, true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isEnabled() || relations.isEmpty() || !MainApplication.isDisplayingMapView()) return;

		Set<IPrimitive> members = new LinkedHashSet<>();	// will contain the list of members to be selected

		for (IRelation<?> r: relations) {					// for each relation selected
			for(IRelationMember<?> rx: r.getMembers()) {	// for each member in that relation
				if (!members.remove(rx.getMember())) {      // if member already in members then remove it
					members.add(rx.getMember());            // otherwise add member to members
				}
			}
		}

		MainApplication.getLayerManager().getActiveData().setSelected(members);
	}
}
