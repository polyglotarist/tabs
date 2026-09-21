package com.guitarist.tabs.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TransposeService {

    private static final String[] SHARP_SCALE =
            {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final String[] FLAT_SCALE =
            {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    private static final Pattern CHORD_PATTERN =
            Pattern.compile("^([A-G])(#|b)?([^/\\s]*)(/([A-G])(#|b)?)?$");

    private static final Pattern BRACKET_PATTERN =
            Pattern.compile("\\[([^\\]]+)]");

    public String transposeChord(String chord, int semitones, boolean useFlats) {
        Matcher m = CHORD_PATTERN.matcher(chord);
        if (!m.matches()) {
            return chord;
        }

        String root = m.group(1) + (m.group(2) != null ? m.group(2) : "");
        String quality = m.group(3);
        String bassRoot = m.group(5);

        String newRoot = shiftNote(root, semitones, useFlats);
        String result = newRoot + quality;

        if (bassRoot != null) {
            String bassAccidental = m.group(6) != null ? m.group(6) : "";
            String newBass = shiftNote(bassRoot + bassAccidental, semitones, useFlats);
            result += "/" + newBass;
        }
        return result;
    }

    public String transposeBody(String body, int semitones, boolean useFlats) {
        Matcher matcher = BRACKET_PATTERN.matcher(body);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String transposed = transposeChord(matcher.group(1), semitones, useFlats);
            matcher.appendReplacement(result, "[" + transposed + "]");
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private String shiftNote(String note, int semitones, boolean useFlats) {
        String[] scale = useFlats ? FLAT_SCALE : SHARP_SCALE;
        int index = indexOf(note);
        int newIndex = ((index + semitones) % 12 + 12) % 12;
        return scale[newIndex];
    }

    private int indexOf(String note) {
        for (int i = 0; i < SHARP_SCALE.length; i++) {
            if (SHARP_SCALE[i].equals(note) || FLAT_SCALE[i].equals(note)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown note: " + note);
    }
}