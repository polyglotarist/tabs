package com.guitarist.tabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TransposeServiceTest {

    private TransposeService transposeService;

    @BeforeEach
    void setUp() {
        transposeService = new TransposeService();
    }

    @Test
    void transposeChord_simpleMajorChord_sharpsUp() {
        assertThat(transposeService.transposeChord("G", 2, false)).isEqualTo("A");
    }

    @Test
    void transposeChord_minorChord_preservesQuality() {
        assertThat(transposeService.transposeChord("Em", 2, false)).isEqualTo("F#m");
    }

    @Test
    void transposeChord_seventhChord_preservesQuality() {
        assertThat(transposeService.transposeChord("Cmaj7", 2, false)).isEqualTo("Dmaj7");
    }

    @Test
    void transposeChord_negativeSemitones_wrapsAroundCorrectly() {
        assertThat(transposeService.transposeChord("C", -3, false)).isEqualTo("A");
    }

    @Test
    void transposeChord_flatsRequested_usesFlatSpelling() {
        assertThat(transposeService.transposeChord("Em", -3, true)).isEqualTo("Dbm");
    }

    @Test
    void transposeChord_slashChord_transposesBothRootAndBass() {
        assertThat(transposeService.transposeChord("D/F#", 2, false)).isEqualTo("E/G#");
    }

    @Test
    void transposeChord_fullOctaveUp_returnsOriginalNote() {
        assertThat(transposeService.transposeChord("G", 12, false)).isEqualTo("G");
    }

    @Test
    void transposeChord_zeroSemitones_returnsUnchanged() {
        assertThat(transposeService.transposeChord("Bm7", 0, false)).isEqualTo("Bm7");
    }

    @ParameterizedTest
    @CsvSource({
            "C, 1, false, C#",
            "C, 1, true, Db",
            "B, 1, false, C",
            "B, 2, false, C#",
            "A, -1, false, G#",
            "A, -1, true, Ab"
    })
    void transposeChord_chromaticScaleWraparound(String input, int semitones, boolean useFlats, String expected) {
        assertThat(transposeService.transposeChord(input, semitones, useFlats)).isEqualTo(expected);
    }

    @Test
    void transposeBody_multipleChordsInline_transposesAllOccurrences() {
        String body = "[G]Somewhere over the [D]rainbow";
        String result = transposeService.transposeBody(body, 2, false);
        assertThat(result).isEqualTo("[A]Somewhere over the [E]rainbow");
    }

    @Test
    void transposeBody_preservesLyricTextExactly() {
        String body = "[C]Hello world, this has [G]no chord brackets around lyrics";
        String result = transposeService.transposeBody(body, 0, false);
        assertThat(result).contains("Hello world, this has")
                .contains("no chord brackets around lyrics");
    }

    @Test
    void transposeBody_emptyBody_returnsEmptyString() {
        assertThat(transposeService.transposeBody("", 2, false)).isEqualTo("");
    }
}