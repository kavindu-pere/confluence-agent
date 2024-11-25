import * as path from 'path';
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  build: {
    outDir: 'dist',
    watch: process.env.NODE_ENV === 'development' ? {} : null,
    rollupOptions: {
      input: 'src/widget/index.tsx',
      output: {
        entryFileNames: 'widget.js',
        format: 'iife'
      }
    }
  },
  resolve: {
    alias: {
      'Frontend': path.resolve(__dirname, '../frontend')
    }
  }
});