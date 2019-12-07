<template>
    <div>
        <section class="row">
            <div class="small-6 columns">
                <h1 class="text-center">YOU</h1>
                <div class="healthbar">
                    <div
                            class="healthbar text-center"
                            style="background-color: green; margin: 0; color: white;"
                            :style="{width: opponents.player.health + '%'}">
                        {{opponents.player.health}}
                    </div>
                </div>
            </div>
            <div class="small-6 columns">
                <h1 class="text-center">MONSTER</h1>
                <div class="healthbar">
                    <div
                            class="healthbar text-center"
                            style="background-color: green; margin: 0; color: white;"
                            :style="{width: opponents.monster.health + '%'}">
                        {{opponents.monster.health}}
                    </div>
                </div>
            </div>
        </section>
        <section class="row controls" v-if="!game.isRunning">
            <div class="small-12 columns">
                <button id="start-game" @click="startGame">START NEW GAME</button>
            </div>
        </section>
        <section class="row controls" v-else>
            <div class="small-12 columns">
                <button id="attack" @click="attack">ATTACK</button>
                <button id="special-attack" @click="specialAttack">SPECIAL ATTACK</button>
                <button id="heal" @click="heal">HEAL</button>
                <button id="give-up" @click="giveUp">GIVE UP</button>
            </div>
        </section>
        <section class="row log">
            <div class="small-12 columns">
                <ul>
                    <li>

                    </li>
                </ul>
            </div>
        </section>
    </div>
</template>


<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";

    const PLAYER_MAX_DAMAGE = 10;
    const PLAYER_MIN_DAMAGE = 3;
    const MONSTER_MAX_DAMAGE = 12;
    const MONSTER_MIN_DAMAGE = 5;

    @Component
    export default class MonsterSlayerGame extends Vue {

        private game = {
            isRunning: false
        };
        private opponents = {
            player: {
                health: 100
            },
            monster: {
                health: 100
            }
        };

        private startGame() {
            this.game.isRunning = true;
            this.opponents.player.health = 100;
            this.opponents.monster.health = 100;
        };

        private attack() {
            const damage = Math.max(Math.floor(Math.random() * PLAYER_MAX_DAMAGE) + 1, PLAYER_MIN_DAMAGE);
            this.opponents.monster.health -= damage;
            const monsterDamage = Math.max(Math.floor(Math.random() * MONSTER_MAX_DAMAGE) + 1, MONSTER_MIN_DAMAGE);
            this.opponents.player.health -= monsterDamage;
        }

        private specialAttack() {

        }

        private heal() {

        }

        private giveUp() {

        }

    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
    .text-center {
        text-align: center;
    }

    .healthbar {
        width: 80%;
        height: 40px;
        background-color: #eee;
        margin: auto;
        transition: width 500ms;
    }

    .controls, .log {
        margin-top: 30px;
        text-align: center;
        padding: 10px;
        border: 1px solid #ccc;
        box-shadow: 0px 3px 6px #ccc;
    }

    .turn {
        margin-top: 20px;
        margin-bottom: 20px;
        font-weight: bold;
        font-size: 22px;
    }

    .log ul {
        list-style: none;
        font-weight: bold;
        text-transform: uppercase;
    }

    .log ul li {
        margin: 5px;
    }

    .log ul .player-turn {
        color: blue;
        background-color: #e4e8ff;
    }

    .log ul .monster-turn {
        color: red;
        background-color: #ffc0c1;
    }

    button {
        font-size: 20px;
        background-color: #eee;
        padding: 12px;
        box-shadow: 0 1px 1px black;
        margin: 10px;
    }

    #start-game {
        background-color: #aaffb0;
    }

    #start-game:hover {
        background-color: #76ff7e;
    }

    #attack {
        background-color: #ff7367;
    }

    #attack:hover {
        background-color: #ff3f43;
    }

    #special-attack {
        background-color: #ffaf4f;
    }

    #special-attack:hover {
        background-color: #ff9a2b;
    }

    #heal {
        background-color: #aaffb0;
    }

    #heal:hover {
        background-color: #76ff7e;
    }

    #give-up {
        background-color: #ffffff;
    }

    #give-up:hover {
        background-color: #c7c7c7;
    }
</style>
