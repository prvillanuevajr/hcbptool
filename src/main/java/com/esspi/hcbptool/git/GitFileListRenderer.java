/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.git;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author presmelito
 */
public class GitFileListRenderer extends JLabel implements ListCellRenderer<GitFile> {

    static Map<GitStatus, Icon> stateIconMap = new HashMap<>();
    static Map<GitStatus, Color> stateBGColorMap = new HashMap<>();

    static {
        buildStateIconMap();
        buildStateColorMap();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends GitFile> list, GitFile file, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(stateIconMap.get(file.getState()));
        setBackground(stateBGColorMap.get(file.getState()));
        setOpaque(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setText(file.getName());
        setFont(new Font(
                this.getFont().getName(),
                isSelected ? Font.ITALIC | Font.BOLD : Font.PLAIN,
                this.getFont().getSize()));
        return this;
    }

    private static void buildStateIconMap() {
        stateIconMap.put(GitStatus.MODIFIED_STAGED, new FlatSVGIcon("com/esspi/hcbptool/svgs/pen-to-square.svg"));
        stateIconMap.put(GitStatus.MODIFIED, new FlatSVGIcon("com/esspi/hcbptool/svgs/pen-to-square.svg"));
        stateIconMap.put(GitStatus.MODIFIED_MODIFIED, new FlatSVGIcon("com/esspi/hcbptool/svgs/pen-to-square.svg"));
        stateIconMap.put(GitStatus.UNTRACKED, new FlatSVGIcon("com/esspi/hcbptool/svgs/question.svg"));
        stateIconMap.put(GitStatus.ADDED_MODIFIED, new FlatSVGIcon("com/esspi/hcbptool/svgs/pen-to-square.svg"));
        stateIconMap.put(GitStatus.ADDED, new FlatSVGIcon("com/esspi/hcbptool/svgs/plus.svg"));
        stateIconMap.put(GitStatus.DELETED, new FlatSVGIcon("com/esspi/hcbptool/svgs/trash-can.svg"));
        stateIconMap.put(GitStatus.DELETED_STAGED, new FlatSVGIcon("com/esspi/hcbptool/svgs/plus.svg"));
    }

    private static void buildStateColorMap() {
        stateBGColorMap.put(GitStatus.MODIFIED, new Color(255, 255, 0, 100));
        stateBGColorMap.put(GitStatus.MODIFIED_STAGED, new Color(255, 255, 0, 100));
        stateBGColorMap.put(GitStatus.MODIFIED_MODIFIED, new Color(255, 255, 0, 100));
        stateBGColorMap.put(GitStatus.UNTRACKED, new Color(0, 0, 255, 50));
        stateBGColorMap.put(GitStatus.ADDED_MODIFIED, new Color(255, 255, 0, 100));
        stateBGColorMap.put(GitStatus.ADDED, new Color(0, 255, 0, 50));
        stateBGColorMap.put(GitStatus.DELETED, new Color(255, 0, 0, 50));
        stateBGColorMap.put(GitStatus.DELETED_STAGED, new Color(255, 0, 0, 50));
    }
    
}
