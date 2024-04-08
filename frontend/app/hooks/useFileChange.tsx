import React, { useState } from 'react'
const MAX_FILE_SIZE = 100000000

export default function useFileChange() {
    const [file, setFile] = useState<File | null>(null)
    const [fileSizeError, setFileSizeError] = useState(false)

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]
        if (!file) return

        if (file.size > MAX_FILE_SIZE) {
            setFile(null)
            setFileSizeError(true)
        } else {
            setFile(file)
            setFileSizeError(false)
        }
    }

    return {
        file,
        fileSizeError,
        handleFileChange
    }
}
