/** @type {import('tailwindcss').Config} */
export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		fontFamily: {
			sans: ['Roboto', 'Calibri'],
			display: ['Poppins', 'Arial Black']
		},
		extend: {
			colors: {
				light: {
					DEFAULT: '#f3e8ee',
					100: '#3e2131',
					200: '#7c4162',
					300: '#b16b92',
					400: '#d2a9c0',
					500: '#f3e8ee',
					600: '#f5ecf1',
					700: '#f8f1f5',
					800: '#faf6f8',
					900: '#fdfafc'
				},
				accent2: {
					DEFAULT: '#bacdb0',
					100: '#242f1e',
					200: '#475d3c',
					300: '#6b8c5a',
					400: '#92af83',
					500: '#bacdb0',
					600: '#c9d7c1',
					700: '#d6e1d0',
					800: '#e4ebe0',
					900: '#f1f5ef'
				},
				accent1: {
					DEFAULT: '#729b79',
					100: '#162018',
					200: '#2d3f30',
					300: '#435f48',
					400: '#5a7f60',
					500: '#729b79',
					600: '#8faf94',
					700: '#abc3af',
					800: '#c7d7ca',
					900: '#e3ebe4'
				},
				accent3: {
					DEFAULT: '#475b63',
					100: '#0e1214',
					200: '#1c2427',
					300: '#2a363b',
					400: '#39484e',
					500: '#475b63',
					600: '#637f89',
					700: '#88a0a9',
					800: '#b0bfc6',
					900: '#d7dfe2'
				},
				dark: {
					DEFAULT: '#2e2c2f',
					100: '#090909',
					200: '#131213',
					300: '#1c1b1c',
					400: '#252426',
					500: '#2e2c2f',
					600: '#59555a',
					700: '#837e85',
					800: '#aca9ae',
					900: '#d6d4d6'
				}
			}
		}
	},
	plugins: []
};
