import { NextRequest, NextResponse } from 'next/server'

export async function GET(req: NextRequest) {
    const res = await fetch('http://localhost:4000/auth/google')

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        console.log(message, error, statusCode)
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const data = await res.json()

    return NextResponse.json(data, { status: 200 })
}
