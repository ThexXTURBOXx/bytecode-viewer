package the.bytecode.club.bytecodeviewer.gui.resourcelist;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.resources.ResourceContainer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Objects;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * @author Konloch
 * @since 6/22/2021
 */
public class ResourceListRightClickRemove extends AbstractAction
{
	private final ResourceListPane resourceListPane;
	private final int x;
	private final int y;
	private final ResourceTree tree;
	
	public ResourceListRightClickRemove(ResourceListPane resourceListPane, int x, int y, ResourceTree tree)
	{
		super("Remove");
		this.resourceListPane = resourceListPane;
		this.x = x;
		this.y = y;
		this.tree = tree;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		TreePath selPath = resourceListPane.tree.getPathForLocation(x, y);
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) Objects.requireNonNull(selPath).getLastPathComponent();
		Enumeration<?> enumeration = resourceListPane.treeRoot.children();
		while (enumeration.hasMoreElements())
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
			if (node.isNodeAncestor(selectNode))
			{
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				root.remove(node);
				
				for (ResourceContainer resourceContainer : BytecodeViewer.resourceContainers)
				{
					if (resourceContainer.name.equals(selectNode.toString()))
					{
						resourceListPane.removeResource(resourceContainer);
						resourceListPane.removeFile(resourceContainer);
						break;
					}
				}
				
				return;
			}
		}
	}
}
