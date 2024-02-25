<script lang="ts">
	export let radius = 430;
	export let color = 'rgb(45, 63, 48)';
</script>

<svg width={radius} height={radius} viewBox="0 0 {radius} {radius}" fill="none" xmlns="http://www.w3.org/2000/svg">
	<circle cx={radius / 2} cy={radius / 2} r={radius / 2} style="fill:{color}" />
</svg>
